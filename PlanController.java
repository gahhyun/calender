package com.ottt.ottt.controller.plan;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ottt.ottt.dao.login.LoginUserDao;
import com.ottt.ottt.dto.CommentDTO;
import com.ottt.ottt.dto.PlanDTO;
import com.ottt.ottt.dto.UserDTO;
import com.ottt.ottt.service.plan.PlanServiceImpl;

@Controller
@RequestMapping(value = "/plan/")
public class PlanController {

	@Autowired
	PlanServiceImpl planService;
	@Autowired
	LoginUserDao loginUserDao;

	
	  @GetMapping("/planList") public String planList(HttpSession session, Model m) throws Exception{
			  
			  //PlanDTO plan = planService.selectPlan(plan_no); m.addAttribute("plan", plan);
			  
			  if(session.getAttribute("id") !=null) { 
				  UserDTO userDTO = loginUserDao.select((String) session.getAttribute("id"));
				  m.addAttribute("userDTO", userDTO); }
			  
			  return "plan/planList"; 

	  }
	  
		//일정 목록 조회  ajax로 목록 가져오기...
	  /*
	  @ResponseBody
		@GetMapping("/getPlanList")
		public List<CommentDTO> getPlanList(HttpSession session) throws Exception {
			
	  		UserDTO userDTO = loginUserDao.select((String)session.getAttribute("id"));
			if(userDTO != null) {
				dto.setUser_no(userDTO.getUser_no());
			}
			return commentService.getCommentList(dto);
		}
	  	
	  	*/

	
	@PostMapping("/write")
	public String writePlan(PlanDTO planDTO, HttpSession session, Model m, RedirectAttributes rattr) {

		System.out.println(">>>>>>>>>>>/plan/write>>>>>>>>>>");
		System.out.println("/plan/write planDTO >>>>>>>>>>> " + planDTO.toString());
		
		try {
				String writer = (String) session.getAttribute("id");
				UserDTO userDTO = loginUserDao.select(writer);
		
				planDTO.setUser_no(userDTO.getUser_no());

		
				int insertResult = planService.insertPlan(planDTO);
				
				if (planService.insertPlan(planDTO) != 1) {
					throw new Exception("WRITE FAIL!");
				}
				
				return "redirect:/plan/planList";
				
		} catch (Exception e) {
			e.printStackTrace();e.printStackTrace();
			return "error";
		}

	}

}