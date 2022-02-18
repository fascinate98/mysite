package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.GalleryRepository;
import com.poscoict.mysite.vo.GalleryVo;

@Service
public class GalleryService {
   @Autowired
   private GalleryRepository galleryRepository;
   
   
   //글 삭제
   public Boolean removeImge(Long no) {
      return galleryRepository.removeImge(no);
   }

   //첫 리스트
   public List<GalleryVo> getImages(){
      List<GalleryVo> list = galleryRepository.getImages();
      return list;
   }
   
   public Boolean saveImage(GalleryVo vo) {
      return galleryRepository.saveImage(vo);
   }
   

}