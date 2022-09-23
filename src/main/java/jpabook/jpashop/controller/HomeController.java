package jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j	//로그 뽑는 법 2
public class HomeController {
	
	//Logger log = LoggerFactory.getLogger(getClass()); : 로그 뽑는 방법 1
	
	@RequestMapping("/")
	public String home() {
		log.info("home controller");   //로그 뽑는 법 2
		return "home";
	}
}
