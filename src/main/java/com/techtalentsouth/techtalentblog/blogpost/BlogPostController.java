package com.techtalentsouth.techtalentblog.blogpost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
///rest controller 
public class BlogPostController {
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	//create static variable to update item everytime bd is updated
		
	@GetMapping(path="/")
	public String index(Model model) {
		//need to pass to thymeleaf-- model
		List<BlogPost> posts = new ArrayList<>();
		posts.removeAll(posts);
		for(BlogPost post: blogPostRepository.findAll()) {
			posts.add(post);
		}
		model.addAttribute("posts", posts);
		
		return "blogpost/index";
	}
	
	@GetMapping(value="/blogposts/new")
	public String newBlog(Model model) {
		BlogPost blogPost = new BlogPost();
		model.addAttribute("blogPost", blogPost);
		return "blogpost/new";
	}
	
		
	@PostMapping(path="/blogposts")
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		BlogPost dbBlogPost = blogPostRepository.save(blogPost);
		
		//add to result page
		model.addAttribute("blogPost", dbBlogPost);
		return "blogpost/result";

	}
	
	//edit a post: add a controller method to edit a single post
	//ID which post to edit via ID of the post in the url (ID --> primary key)
	// */blogposts/{id}
	@GetMapping(path="/blogposts/{id}")
	public String editPostWithId(@PathVariable Long id, Model model) {
		Optional<BlogPost> post = blogPostRepository.findById(id);
		if(post.isPresent()) {
			BlogPost actualPost = post.get();
			model.addAttribute("blogPost", actualPost);
		}
		return "blogpost/edit";
	}
	
	@PostMapping(path="/blogposts/{id}")
	public String updatePost(@PathVariable Long id, BlogPost blogPost, Model model) {
		Optional<BlogPost> post = blogPostRepository.findById(id);
		if(post.isPresent()) {
			BlogPost actualPost = post.get();
			actualPost.setTitle(blogPost.getTitle());
			actualPost.setAuthor(blogPost.getAuthor());
			actualPost.setBlogEntry(blogPost.getBlogEntry());
			blogPostRepository.save(actualPost);
			model.addAttribute("blogPost", actualPost);
		}
		return "blogpost/result";		
	}
	
	@GetMapping(path="blogposts/delete/{id}")
	public String deleteByPostById(@PathVariable Long id) {
		blogPostRepository.deleteById(id);
		return "blogpost/delete";
	}

}