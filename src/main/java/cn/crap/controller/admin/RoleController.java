package cn.crap.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.model.Role;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController<Role>{

	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult list(@ModelAttribute Role role,@RequestParam(defaultValue="1") Integer currentPage){
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		Map<String,Object> map = Tools.getMap("roleName|like",role.getRoleName());
		return new JsonResult(1,roleService.findByMap(map,page,null),page);
	}
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult detail(@ModelAttribute Role role){
		Role model;
		if(!role.getId().equals(Const.NULL_ID)){
			model= roleService.get(role.getId());
		}else{
			model=new Role();
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult addOrUpdate(@ModelAttribute Role role){
		if(!MyString.isEmpty(role.getId())){
			roleService.update(role);
		}else{
			role.setId(null);
			roleService.save(role);
		}
		return new JsonResult(1,role);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult delete(@ModelAttribute Role role){
		roleService.delete(role);
		return new JsonResult(1,null);
	}
}
