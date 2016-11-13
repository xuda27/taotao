package cn.eden.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.eden.taotao.pojo.TbUser;
import cn.eden.taotao.portal.service.UserService;
import cn.eden.taotao.portal.service.impl.UserServiceImpl;
import cn.eden.taotao.util.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;

	/**
	 * 在handler执行之前处理 <br>
	 * 返回值决定hadler是否执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 判断用户是否登录
		// 从cookie中取出token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isBlank(token)) {
			return false;
		}
		// 根据token换取用户信息
		TbUser user = userService.getUserByToken(token);
		// 1.取不到用户信息
		if (user == null) {
			// 跳转到登录页面，把用户请求的url作为参数传递给登录页面
			response.sendRedirect(userService.SSO_BASE_URL
					+ userService.SSO_USER_LOGIN + "?redirect="
					+ request.getRequestURL());
			return false;
		}
		//设置request的user对象 方便下一个页面取user信息
		request.setAttribute("user", user);
		// 2.取到用户信息，放行
		return true;
	}

	/**
	 * handler执行之后，返回ModelAndView之前
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 返回ModelAndView之后 响应用户之后
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
