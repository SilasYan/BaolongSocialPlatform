package icu.baolong.social.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Servlet 工具类
 *
 * @author Silas Yan 2025-04-03:20:21
 */
public class ServletUtil {
	private static final String X_REQUESTED_FOR = "X-Requested-For";
	private static final String X_FORWARDED_FOR = "X-Forwarded-For";
	private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
	private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
	private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
	private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
	private static final String UNKNOWN = "unknown";
	private static final String IP_127 = "127.0.0.1";
	private static final String IP_000 = "0:0:0:0:0:0:0:1";
	private static final String IP_192 = "localhost";
	private static final Pattern IP_PATTERN = Pattern.compile("^((\\d+\\.){3}\\d+)$");
	private static final String COMMA = ",";

	private ServletUtil() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/**
	 * 获取当前请求的 RequestAttributes
	 */
	private static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			return (ServletRequestAttributes) requestAttributes;
		}
		return null;
	}

	/**
	 * 从 SpringBoot 中获取 Request 请求对象
	 *
	 * @return 返回当前请求的 Request 对象
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = getRequestAttributes();
		return attributes != null ? attributes.getRequest() : null;
	}

	/**
	 * 从 SpringBoot 中获取 Response 请求对象
	 *
	 * @return 返回当前请求的 Response 对象
	 */
	public static HttpServletResponse getResponse() {
		ServletRequestAttributes attributes = getRequestAttributes();
		return attributes != null ? attributes.getResponse() : null;
	}

	/**
	 * 获取 Session
	 *
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getSession() : null;
	}

	/**
	 * 获取当前请求方式
	 *
	 * @return 方法名
	 */
	public static String getMethod() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getMethod() : null;
	}

	/**
	 * 获取当前请求路径
	 *
	 * @return 路径
	 */
	public static String getUri() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getRequestURI() : null;
	}

	/**
	 * 获取当前请求地址（全路径）
	 *
	 * @return 地址（全路径）
	 */
	public static String getUrl() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getRequestURL().toString() : null;
	}

	/**
	 * 获取请求协议
	 *
	 * @return 协议
	 */
	public static String getProtocol() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getProtocol() : null;
	}

	/**
	 * 获取请求IP地址
	 *
	 * @return IP地址
	 */
	public static String getRemoteAddr() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getRemoteAddr() : null;
	}

	/**
	 * 获取Cookie数组
	 *
	 * @return Cookie数组
	 */
	public static Cookie[] getCookies() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getCookies() : null;
	}

	/**
	 * 获取Cookie集合
	 *
	 * @return 不可修改的Cookie Map
	 */
	public static Map<String, Cookie> getCookieMap() {
		Cookie[] cookies = getCookies();
		if (cookies == null || cookies.length == 0) {
			return Collections.emptyMap();
		}

		Map<String, Cookie> cookieMap = new HashMap<>(cookies.length);
		for (Cookie cookie : cookies) {
			cookieMap.put(cookie.getName(), cookie);
		}
		return Collections.unmodifiableMap(cookieMap);
	}

	/**
	 * 获取指定Cookie
	 *
	 * @param name Cookie名称
	 * @return 对应的Cookie对象，不存在则返回null
	 */
	public static Cookie getCookie(String name) {
		if (name == null) {
			return null;
		}
		return getCookieMap().get(name);
	}

	/**
	 * 获取请求头名称枚举
	 *
	 * @return 请求头名称枚举
	 */
	public static Enumeration<String> getHeaderNames() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getHeaderNames() : Collections.emptyEnumeration();
	}

	/**
	 * 获取指定请求头
	 *
	 * @param name 请求头名称
	 * @return 请求头值
	 */
	public static String getHeader(String name) {
		HttpServletRequest request = getRequest();
		return request != null ? request.getHeader(name) : null;
	}

	/**
	 * 获取请求参数名称枚举
	 *
	 * @return 参数名称枚举
	 */
	public static Enumeration<String> getParameterNames() {
		HttpServletRequest request = getRequest();
		return request != null ? request.getParameterNames() : Collections.emptyEnumeration();
	}

	/**
	 * 获取请求参数
	 *
	 * @param name 参数名称
	 * @return 参数值
	 */
	public static String getParameter(String name) {
		HttpServletRequest request = getRequest();
		return request != null ? request.getParameter(name) : null;
	}

	/**
	 * 获取请求参数数组
	 *
	 * @param name 参数名称
	 * @return 参数值数组
	 */
	public static String[] getParameterValues(String name) {
		HttpServletRequest request = getRequest();
		return request != null ? request.getParameterValues(name) : null;
	}

	/**
	 * 获取所有请求参数集合
	 *
	 * @return 不可修改的参数Map
	 */
	public static Map<String, String[]> getParameterMap() {
		HttpServletRequest request = getRequest();
		return request != null ? Collections.unmodifiableMap(request.getParameterMap()) : Collections.emptyMap();
	}

	/**
	 * 获取客户端真实IP地址
	 *
	 * @return IP地址
	 */
	public static String getIp() {
		return getIp(getRequest());
	}

	/**
	 * 获取客户端真实IP地址
	 *
	 * @param request HttpServletRequest
	 * @return IP地址
	 */
	public static String getIp(HttpServletRequest request) {
		if (request == null) {
			return null;
		}

		String ip = checkHeaderForIp(request, X_FORWARDED_FOR);
		if (ip == null) {
			ip = checkHeaderForIp(request, X_REQUESTED_FOR);
		}
		if (ip == null) {
			ip = checkHeaderForIp(request, PROXY_CLIENT_IP);
		}
		if (ip == null) {
			ip = checkHeaderForIp(request, WL_PROXY_CLIENT_IP);
		}
		if (ip == null) {
			ip = checkHeaderForIp(request, HTTP_CLIENT_IP);
		}
		if (ip == null) {
			ip = checkHeaderForIp(request, HTTP_X_FORWARDED_FOR);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return parseFirstValidIp(ip);
	}

	private static String checkHeaderForIp(HttpServletRequest request, String headerName) {
		String ip = request.getHeader(headerName);
		if (ip != null && !ip.isEmpty() && !UNKNOWN.equalsIgnoreCase(ip)) {
			return ip;
		}
		return null;
	}

	private static String parseFirstValidIp(String ip) {
		if (ip == null || !ip.contains(COMMA)) {
			return ip;
		}

		String[] ips = ip.split(COMMA);
		for (String strIp : ips) {
			strIp = strIp.trim();
			if (IP_PATTERN.matcher(strIp).matches()) {
				return strIp;
			}
		}
		return ips[0].trim();
	}

	/**
	 * 获取客户端MAC地址
	 *
	 * @param request HttpServletRequest
	 * @return MAC地址
	 */
	public static String getMac(HttpServletRequest request) {
		String ip = getIp(request);
		if (ip == null) {
			return "";
		}

		try {
			if (IP_127.equals(ip) || IP_000.equals(ip) || IP_192.equals(ip)) {
				return getLocalMacAddress();
			} else {
				return getRemoteMacAddress(ip);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to get MAC address", e);
		}
	}

	private static String getLocalMacAddress() throws IOException {
		NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		byte[] mac = network.getHardwareAddress();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			sb.append(String.format("%02X", mac[i]));
		}
		return sb.toString();
	}

	private static String getRemoteMacAddress(String ip) throws IOException {
		Process process = Runtime.getRuntime().exec("arp -A " + ip);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			return br.lines()
					.filter(line -> line.contains(ip))
					.findFirst()
					.map(line -> line.substring(line.indexOf(ip) + ip.length() + 10, line.indexOf(ip) + ip.length() + 27))
					.map(String::trim)
					.map(String::toUpperCase)
					.orElse("");
		}
	}
}

