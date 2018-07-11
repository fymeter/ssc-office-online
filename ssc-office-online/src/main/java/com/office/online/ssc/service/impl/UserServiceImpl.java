package com.office.online.ssc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.online.ssc.domain.Users;
import com.office.online.ssc.domain.UsersExample;
import com.office.online.ssc.mapper.UsersMapper;
import com.office.online.ssc.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersMapper usersMapper;

	@Override
	public Users login(String j_username, String password) {
		UsersExample example = new UsersExample();
		example.createCriteria().andUsernameEqualTo(j_username).andPasswordEqualTo(password);
		List<Users> list = this.usersMapper.selectByExample(example);
		return list !=null ? list.get(0) : null;
	}

}
