package ru.geekbrains.supershop.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.geekbrains.supershop.beans.Cart;
import ru.geekbrains.supershop.persistence.entities.Review;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.persistence.pojo.ReviewPojo;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;
import ru.geekbrains.supershop.utils.CaptchaGenerator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final Cart cart;
    private final CaptchaGenerator captchaGenerator;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ShopuserService shopuserService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model, @RequestParam(required = false) Integer category) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category));
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        if (data != null) {
            System.out.println(data);
        }

        model.addAttribute("products", productService.findAll(null));
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "admin";
    }

    @PostMapping("/admin/visible")
    public String setReviewVisible(ReviewPojo reviewPojo){
        Review review = reviewService.getReviewById(UUID.fromString(reviewPojo.getId())).orElse(new Review());
        reviewService.setVisible(review, reviewPojo.isVisible());
        return "redirect:/admin";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        Shopuser shopuser = shopuserService.findByPhone(principal.getName());

        model.addAttribute("reviews", reviewService.getReviewsByShopuser(shopuser).orElse(new ArrayList<>()));
        model.addAttribute("shopuser", shopuser);

        if (data != null) {
            System.out.println(data);
        }

        return "profile";
    }

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] captcha(HttpSession session) {
        try {
            BufferedImage img = captchaGenerator.getCaptchaImage();
            session.setAttribute("captchaCode", captchaGenerator.getCaptchaString());
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(img, "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}