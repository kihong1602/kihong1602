package com.blanc.recrute.mybatis;

import com.blanc.recrute.member.dto.LoginDTO;
import com.blanc.recrute.member.dto.MemberDTO;
import com.blanc.recrute.member.dto.MemberInfoDTO;


public interface MemberMapper {

  int saveMember(MemberInfoDTO memberInfoDTO);

  Integer duplicateId(MemberDTO memberDTO);

  String findEmailById(MemberDTO memberDTO);

  String loginCheck(LoginDTO loginDTO);

  int emailAuth(MemberDTO memberDTO);
}
