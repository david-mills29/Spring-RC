package cz.jiripinkas.jba.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.jiripinkas.jba.service.BlogService;

@Controller
public class BlogIconController {

	@Autowired
	private BlogService blogService;

	@RequestMapping(value = "/icon/{blogId}", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getBlogIcon(@PathVariable int blogId) throws IOException {
		return blogService.getIcon(blogId);
	}
}