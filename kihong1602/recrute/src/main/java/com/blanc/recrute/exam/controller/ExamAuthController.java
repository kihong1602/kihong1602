package com.blanc.recrute.exam.controller;

import com.blanc.recrute.common.CookieManager;
import com.blanc.recrute.common.JsonUtil;
import com.blanc.recrute.common.TimeUnit;
import com.blanc.recrute.common.ViewResolver;
import com.blanc.recrute.common.Word;
import com.blanc.recrute.exam.dto.AptIdDTO;
import com.blanc.recrute.exam.dto.RecruitInfoDTO;
import com.blanc.recrute.exam.service.ExamService;
import com.blanc.recrute.member.dto.InvalidDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "exam", value = "/exam/auth/*")
public class ExamAuthController extends HttpServlet {

  private final ExamService EXAM_SERVICE = new ExamService();
  private final String EXAM_AUTH = "examAuth";

  //시험 인증 관련

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    //파라미터는 recruitment_id와 apt_id가 들어오는걸로 임시 지정, recruitId는 구색맞추기 -> 추후 제거예정
//    Integer recruitId = Integer.parseInt(request.getParameter("recruitmentId"));
    String aptId = request.getParameter("aptId");
    RecruitInfoDTO examDTO = EXAM_SERVICE.getRecruitContent(aptId);

    sendExamAuth(request, response, aptId);
    request.setAttribute("examDTO", examDTO);

    String path = "exam/exam-auth";
    ViewResolver.render(path, request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

//    String parsingJson = JsonUtil.jsonParsing(request);
    AptIdDTO aptIdDTO = JsonUtil.JsonParser(request, AptIdDTO.class);

    Cookie examAuth = CookieManager.getCookie(request, EXAM_AUTH);

    InvalidDTO invalidDTO;
    if (examAuth != null) {

      String sessionAptId = CookieManager.getSessionValue(request, examAuth);
      invalidDTO = sessionAptId.equals(aptIdDTO.getAptId()) ? new InvalidDTO(Word.AVAILABLE)
          : new InvalidDTO(Word.UNAVAILABLE);

    } else {
      invalidDTO = new InvalidDTO(Word.FAIL);
    }

    JsonUtil.sendJSON(response, invalidDTO);

  }


  private void sendExamAuth(HttpServletRequest request, HttpServletResponse response,
      String aptId) {

    String cookieValue = String.valueOf(UUID.randomUUID());
    request.getSession().setAttribute(cookieValue, aptId);

    Cookie examAuthCookie = CookieManager.createCookie(EXAM_AUTH, cookieValue,
                                                       TimeUnit.HOUR.getValue());
    response.addCookie(examAuthCookie);

  }
}


