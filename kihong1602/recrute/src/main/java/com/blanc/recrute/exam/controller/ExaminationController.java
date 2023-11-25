package com.blanc.recrute.exam.controller;

import static com.blanc.recrute.common.Word.AVAILABLE;
import static com.blanc.recrute.common.Word.SUCCESS;
import static com.blanc.recrute.common.Word.UNAVAILABLE;

import com.blanc.recrute.common.JsonUtil;
import com.blanc.recrute.common.URLParser;
import com.blanc.recrute.common.ViewResolver;
import com.blanc.recrute.common.Word;
import com.blanc.recrute.exam.dto.ExaminationDTO;
import com.blanc.recrute.exam.dto.answer.AnswerData;
import com.blanc.recrute.exam.service.ExamService;
import com.blanc.recrute.member.dto.InvalidDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "exam/*", value = "/exam/*")
public class ExaminationController extends HttpServlet {

  private final ExamService EXAM_SERVICE = new ExamService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String path = "exam/examination";

    Integer examId = URLParser.getLastURI(request);
    List<ExaminationDTO> list = EXAM_SERVICE.getExamination(examId);

    request.setAttribute("ExamList", list);
    request.setAttribute("size", list.size());
    ViewResolver.render(path, request, response);
  }

  /**
   * 현재 exam table에 question, answer 컬럼이 종속되어 있으므로 답안을 DB에 저장할수 없습니다. 따라서 현재는 DAO까지 잘 전달되는지 확인을 해보았고, DB 재설계 후 답안을 DB에
   * insert 하는 로직으로 변경예정입니다.
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    AnswerData answerData = JsonUtil.jsonParser(request, AnswerData.class);

    Word result = EXAM_SERVICE.saveExamination(answerData);

    InvalidDTO invalidDTO =
        result.equals(SUCCESS) ? new InvalidDTO(AVAILABLE) : new InvalidDTO(UNAVAILABLE);

    JsonUtil.sendJSON(response, invalidDTO);
  }
}
