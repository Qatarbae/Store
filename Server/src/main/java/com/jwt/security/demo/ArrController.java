package com.jwt.security.demo;

import com.jwt.security.Entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

@Controller
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class ArrController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<ArrayList<Arr>> getArr(@AuthenticationPrincipal User user){
        String image = "1679955190_phonoteka-org-p-overlord-neironist-art-vkontakte-4.png";
        Arr a = new Arr("title", "author", uploadPath+"/img/"+image, 10.0, 200, 300, 10);
        Arr b = new Arr("title1", "author", uploadPath+"/img/"+image, 10.0, 200, 300, 1);
        Arr c = new Arr("title2", "author", uploadPath+"/img/"+image, 10.0, 200, 300, 2);
        ArrayList<Arr> arrayList = new ArrayList<>();
        arrayList.add(a);
        arrayList.add(b);
        arrayList.add(c);
        return ResponseEntity.ok(arrayList);
    }
}