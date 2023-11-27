package com.blanc.recrute.recruitment.dao;

import com.blanc.recrute.common.Word;
import com.blanc.recrute.mybatis.MybatisConnectionFactory;
import com.blanc.recrute.mybatis.RecruitMapper;
import com.blanc.recrute.recruitment.dto.ApplyDTO;
import com.blanc.recrute.recruitment.dto.DetailDTO;
import com.blanc.recrute.recruitment.dto.RecruitDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;

public class RecruitDAO {

  private final Logger LOGGER = Logger.getLogger(RecruitDAO.class.getName());

  public DetailDTO findRctDetail(RecruitDTO recruitDTO) {

    DetailDTO detailDTO;

    try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
      RecruitMapper recruitMapper = sqlSession.getMapper(RecruitMapper.class);
      detailDTO = recruitMapper.findRctDetailById(recruitDTO);

    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, Word.ERROR.value(), e);
      detailDTO = null;
    }

    return detailDTO;
  }

  public Integer apply(ApplyDTO applyDTO) {

    Integer result;
    try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
      RecruitMapper recruitMapper = sqlSession.getMapper(RecruitMapper.class);

      result = recruitMapper.saveApply(applyDTO);
      sqlSession.commit();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, Word.ERROR.value(), e);
      result = null;
    }

    return result;
  }

  public Integer findMemberPk(String memberRealId) {

    Integer id;
    try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
      RecruitMapper recruitMapper = sqlSession.getMapper(RecruitMapper.class);

      id = recruitMapper.findIdByMemberPk(memberRealId);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, Word.ERROR.value(), e);
      id = null;
    }

    return id;
  }

}
