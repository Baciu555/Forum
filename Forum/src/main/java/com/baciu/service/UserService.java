package com.baciu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baciu.entity.User;
import com.baciu.exception.AccountBannedException;
import com.baciu.exception.EmailExistsException;
import com.baciu.exception.FileUploadException;
import com.baciu.exception.UserNotExistsException;
import com.baciu.exception.UsernameExistsException;
import com.baciu.repository.UserRepository;

@Service
public class UserService {

	private final Path UPLOADED_FOLDER = Paths.get("uploads");
	private final String DEFAULT_AVATAR_NAME = "default-avatar.jpg";
	private final String DEFAULT_PERMISSION = "user";
	private final Integer DEFAULT_BAN_COUNT = 0;
	
	@Autowired
	private UserRepository userRepository;

	public User logIn(String username, String password) throws UserNotExistsException, AccountBannedException {
		User user = userRepository.findByUsernameAndPassword(username, password);
		
		if (user == null)
			throw new UserNotExistsException();
		
		
		if (user.getBanCount() >= 3)
			throw new AccountBannedException("Konto zbanowane na zawsze");
		
		Date currentDate = new Date();
		if (user.getBanExpire() != null && user.getBanExpire().after(currentDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String msg = "Konto zbanowane do : " + sdf.format(user.getBanExpire());
			throw new AccountBannedException(msg);
		}
		
		if (user.getBanExpire() != null && !user.getBanExpire().after(currentDate)) {
			user.setBanExpire(null);
			user = userRepository.save(user);
		}
		
		return user;
	}

	public User getById(long userId) {
		User user = userRepository.findOne(userId);
		
		if (user == null) return null;
		
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
		
		user.setAvatarPath(DEFAULT_AVATAR_NAME);
		user.setPermission(DEFAULT_PERMISSION);
		user.setBanCount(DEFAULT_BAN_COUNT);
		user.setJoinDate(new Date());
		
		userRepository.save(user);
	}

	public void update(User user, String passwordConfirm, MultipartFile file) throws FileUploadException, Exception {
		if (!passwordConfirm.equals(user.getPassword()))
			throw new Exception("Hasła nie są zgodne");

		User usr = getById(user.getId());
		
		if (file.isEmpty()) {
			user.setAvatarPath(usr.getAvatarPath());
		}

		if (!file.isEmpty() && !usr.getAvatarPath().equals(file.getOriginalFilename())) {
			uploadAvatar(file);
			
			if (!usr.getAvatarPath().equals(DEFAULT_AVATAR_NAME))
				deleteAvatarFile(usr.getAvatarPath());
		}

		user.setAvatarPath(file.getOriginalFilename());
		user.setUsername(usr.getUsername());
		user.setPassword(usr.getPassword());
		user.setEmail(usr.getEmail());
		user.setBanExpire(usr.getBanExpire());
		
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
		return userRepository.getBestUsers();
	}
}
