package guat.lxy.bigdata.pig.controller;



import guat.lxy.bigdata.pig.Mapper.LoginUserMapper;
import guat.lxy.bigdata.pig.POJO.Dept;
import guat.lxy.bigdata.pig.POJO.LoginUser;
import guat.lxy.bigdata.pig.service.SendMailService;
import guat.lxy.bigdata.pig.service.VerificationCodeService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class WelcomeController {
    @Autowired
    LoginUserMapper loginUserMapper;
    @Autowired
    private SendMailService mailService;
    @Autowired
    private VerificationCodeService codeService;
    @PostMapping("/doLogin")
    public String doLogin(@ModelAttribute("loginuser") LoginUser loginuser, Model model) {
        if (!Objects.equals(loginUserMapper.getPasswordWithLoginUser(loginuser.getUserName()), loginuser.getPassword())) {
            model.addAttribute("errorMsg", "用户名或密码错误！");
            return "login";
        }
        model.addAttribute("name",loginuser.getUserName());
        model.addAttribute("today",new Date());
        List<Dept> depts = new ArrayList<>();
        depts.add(new Dept(1, "研发部"));
        depts.add(new Dept(2, "生产部"));
        model.addAttribute("depts", depts);
        return "Welcome";
    }
    @PostMapping("/sendLoginCode")
    @ResponseBody
    public String sendLoginCode(@RequestParam String email) {
        try {
            String code = codeService.generateAndSaveCode(email);
            mailService.sendVerificationCode(email, code);
            return "验证码已发送至 " + email;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "邮件发送失败，请稍后重试";
        }
    }
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginuser", new LoginUser());
        return "login";
    }
    @PostMapping("/doLoginByCode")
    public String doLoginByCode(@RequestParam String email,
                                @RequestParam String code,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        boolean valid = codeService.verifyCode(email, code);
        if (!valid) {
            redirectAttributes.addFlashAttribute("errorMsg", "验证码错误或已过期！");
            return "redirect:/login";
        }
        model.addAttribute("name", email);
        model.addAttribute("today", new Date());
        List<Dept> depts = new ArrayList<>();
        depts.add(new Dept(1, "研发部"));
        depts.add(new Dept(2, "生产部"));
        model.addAttribute("depts", depts);
        return "Welcome";
    }
}
