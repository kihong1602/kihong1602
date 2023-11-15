package com.blanc.recrute.member.controller;

import static com.blanc.recrute.common.Word.AVAILABLE;
import static com.blanc.recrute.common.Word.BLANK;
import static com.blanc.recrute.common.Word.UNAVAILABLE;

import com.blanc.recrute.common.JsonUtil;
import com.blanc.recrute.common.Word;
import com.blanc.recrute.member.dto.IdCheckDTO;
import com.blanc.recrute.member.dto.InvalidDTO;
import com.blanc.recrute.member.service.MemberService;
import com.blanc.recrute.member.service.MemberServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "check-id", value = "/check-id")
public class IdCheckController extends HttpServlet {

  private final MemberService MEMBER_SERVICE = new MemberServiceImpl();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    IdCheckDTO idCheckDTO = JsonUtil.JsonParser(request, IdCheckDTO.class);
    String memberId = idCheckDTO.getMemberId();

    Word check = MEMBER_SERVICE.idCheck(memberId);

    InvalidDTO invalidDTO = switch (check) {
      case EXIST -> new InvalidDTO(UNAVAILABLE);
      case NONE -> new InvalidDTO(AVAILABLE);
      case BLANK -> new InvalidDTO(BLANK);
      default -> null;
    };

    JsonUtil.sendJSON(response, invalidDTO);
  }
}
