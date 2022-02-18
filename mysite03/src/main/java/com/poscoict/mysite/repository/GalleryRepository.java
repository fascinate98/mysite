package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
   
   @Autowired
   private SqlSession sqlSession;
   
   public List<GalleryVo> getImages() {
      Map<String, Object> map = new HashMap<>();
      List<GalleryVo> list = sqlSession.selectList("gallery.getImages",map);
      return list;
   }

   
   public boolean removeImge(long no) {
      int count = sqlSession.delete("gallery.removeImge", no);
      return count==1;
   }
   
   public boolean saveImage(GalleryVo vo) {
      int count = sqlSession.delete("gallery.saveImage", vo);
      return count==1;
   }

}