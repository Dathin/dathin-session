package io.github.dathin.dathinsession.dao;

import io.github.dathin.dathinsession.model.user.UserRequest;
import io.github.dathin.dathinsession.model.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDao {

	private final JdbcTemplate jdbcTemplate;

	public UserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(UserRequest userRequest) {
		jdbcTemplate.update("INSERT INTO USERS (EMAIL, PASSWORD) VALUES (?, ?)", userRequest.getEmail(),
				userRequest.getPassword());
	}

	public Optional<User> getPasswordToLogin(UserRequest userRequest) {
		return jdbcTemplate.query("SELECT * FROM USERS WHERE EMAIL = ? LIMIT 1", optionalUserResultSetExtractor,
				userRequest.getEmail());
	}

	private final ResultSetExtractor<Optional<User>> optionalUserResultSetExtractor = resultSet -> {
		if (resultSet.next()) {
			var user = resultSetUser(resultSet);
			return Optional.of(user);
		}
		return Optional.empty();
	};

	private User resultSetUser(ResultSet resultSet) throws SQLException {
		var user = new User();
		user.setId(resultSet.getInt("ID"));
		user.setEmail(resultSet.getString("EMAIL"));
		user.setPassword(resultSet.getString("PASSWORD"));
		return user;
	}

}
