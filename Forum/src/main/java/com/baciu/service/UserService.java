package com.baciu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baciu.entity.CurrentUser;
import com.baciu.entity.Role;
import com.baciu.entity.User;
import com.baciu.exception.EmailExistsException;
import com.baciu.exception.FileUploadException;
import com.baciu.exception.UsernameExistsException;
import com.baciu.repository.UserRepository;

@Service
public class UserService {

	private final Path UPLOADED_FOLDER = Paths.get("uploads");
	private final String DEFAULT_AVATAR_NAME = "default-avatar.jpg";
	private final Long DEFAULT_ROLE = 1l;
	private final Integer DEFAULT_BAN_COUNT = 0;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

//	public User logIn(String username, String password) throws UserNotExistsException, AccountBannedException {
//		User user = userRepository.findByUsernameAndPassword(username, password);
//		
//		if (user == null)
//			throw new UserNotExistsException();
//		
//		
//		if (user.getBanCount() >= 3)
//			throw new AccountBannedException("Konto zbanowane na zawsze");
//		
//		Date currentDate = new Date();
//		if (user.getBanExpire() != null && user.getBanExpire().after(currentDate)) {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			String msg = "Konto zbanowane do : " + sdf.format(user.getBanExpire());
//			throw new AccountBannedException(msg);
//		}
//		
//		if (user.getBanExpire() != null && !user.getBanExpire().after(currentDate)) {
//			user.setBanExpire(null);
//			user = userRepository.save(user);
//		}
//		
//		return user;
//	}

	public User getById(long userId) {
		User user = userRepository.findOne(userId);
		return user;
	}
	
	public User getByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

	public void register(User user, String passwordConf)
			throws InputMismatchException, EmailExistsException, UsernameExistsException {

		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new UsernameExistsException("Nazwa zajęta");
		}
		
		if (!user.getPassword().equals(passwordConf)) {
			throw new InputMismatchException("Hasła nie są identyczne");
		}

		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new EmailExistsException("Email zajęty");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAvatarPath(DEFAULT_AVATAR_NAME);
		
		Role role = new Role();
		role.setId(DEFAULT_ROLE);
		Set<Role> roles = new HashSet<>(0);
		roles.add(role);
		
		user.setRoles(roles);
		user.setBanCount(DEFAULT_BAN_COUNT);
		user.setJoinDate(new Date());
		
		userRepository.save(user);
	}

	public void updateData(User user) throws Exception {
		User usr = userRepository.findOne(user.getId());
		if (userRepository.findByEmail(user.getEmail()) != null && !usr.getEmail().equals(user.getEmail()))
			throw new Exception("email zajęty");
		
		if (userRepository.findByUsername(user.getUsername()) != null && !usr.getUsername().equals(user.getUsername()))
			throw new Exception("nazwa zajęta");
		
		usr.setUsername(user.getUsername());
		usr.setEmail(user.getEmail());
		
		userRepository.save(usr);
	}
	
	public void updatePassword(String password, String passwordConfirm) throws Exception {
		if (password.length() < 1 || password.length() > 250)
			throw new Exception("Długość hasła powinna mieścic się w granicach 1 - 250 znaków");
			
		if (!password.equals(passwordConfirm)) {
			throw new Exception("Hasła nie są zgodne");
		}
		
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findOne(currentUser.getId());
		user.setPassword(password);
		userRepository.save(user);
	}
	
	public void updateAvatar(MultipartFile file) throws Exception {
		if (file.isEmpty())
			throw new FileUploadException("plik pusty");
		
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findOne(currentUser.getId());

		if (!user.getAvatarPath().equals(file.getOriginalFilename())) {
			uploadAvatar(file);
			
			if (!user.getAvatarPath().equals(DEFAULT_AVATAR_NAME))
				deleteAvatarFile(user.getAvatarPath());
		}
		
		user.setAvatarPath(file.getOriginalFilename());
		userRepository.save(user);
	}

	public void uploadAvatar(MultipartFile file) throws FileUploadException {
		if (!isCorrectFileFormat(file))
			throw new FileUploadException("Niepoprawny format pliku");
		
		try {
			Files.copy(file.getInputStream(), UPLOADED_FOLDER.resolve(file.getOriginalFilename()));
		} catch (FileAlreadyExistsException fileAlreadyExistsException) {
			throw new FileUploadException("Nazwa pliku już istnieje, aby załadować plik zmień jego nazwe");
		} catch (IOException ioException) {
			throw new FileUploadException("Wystąpił błąd spróbuj ponownie");
		}
	}
	
	private boolean isCorrectFileFormat(MultipartFile file) {
		String[] correctFileFormats = {"image/gif", "image/jpeg", "image/png"};
		if (Arrays.asList(correctFileFormats).contains(file.getContentType()))
			return true;
		
		return false;
	}

	public void deleteAvatarFile(String avatarName) throws Exception {
		try {
			File file = new File(UPLOADED_FOLDER + "/" + avatarName);
			file.delete();
		} catch (NullPointerException nullPointerException) {
			throw new Exception("Plik do usuniecia nie istnieje");
		}
	}
	
	public void banUser(long userId, Date date) throws NullPointerException, Exception {
		Date currentDate = new Date();
		if (date.after(currentDate)) {
			User user = userRepository.findOne(userId);
			user.setBanExpire(date);
			user.setBanCount(user.getBanCount() + 1);
			userRepository.save(user);
		} else {
			System.out.println("Źle");
			throw new Exception("Data musi poprzedziać dzien dzisiejszy");
		}
	}
	
	public List<User> getBestUsers() {
		List<User> bestUsers = userRepository.getBestUsers();
		if (bestUsers.size() < 5)
			return bestUsers.subList(0, bestUsers.size());
		
		return bestUsers.subList(0, 5);
	}
}
