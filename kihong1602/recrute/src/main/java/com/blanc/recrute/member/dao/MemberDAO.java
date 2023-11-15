package com.blanc.recrute.member.dao;

import com.blanc.recrute.member.dto.LoginDTO;
import com.blanc.recrute.member.dto.MemberDTO;
import com.blanc.recrute.member.dto.MemberInfoDTO;

public interface MemberDAO {

  int insertMember(MemberInfoDTO memberDTO);

  String loginCheck(LoginDTO loginDTO);

  int idCheck(String id);

  String findEmail(MemberDTO memberDTO);

  int authGrantMember(MemberDTO memberDTO);
}
