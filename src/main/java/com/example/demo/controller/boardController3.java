package com.example.demo.controller;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.Board3;
import com.example.demo.dao.BoardMapper1;

@Controller
public class boardController3 {

	@Autowired
	BoardMapper1 boardService;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/")
	public String main(){
		return "redirect:boardList/1";
	}
	@RequestMapping(value = "boardForm", method = RequestMethod.POST)
	public String boardForm(@ModelAttribute Board3 board, Model model, HttpServletRequest request) {

		int pagenum = Integer.parseInt(request.getParameter("pagenum"));
		model.addAttribute("pagenum", pagenum);

		return "boardForm3";

	}

	@RequestMapping(value = "boardUpdate", method = RequestMethod.POST)
	public String boardUpdate(@ModelAttribute Board3 board, Model model, HttpServletRequest request) {

		int pagenum = Integer.parseInt(request.getParameter("pagenum"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));

		board = boardService.boardView3(board_num);

		List<String> list = new ArrayList<String>();
		String img = board.getBoard_img();

		String[] img2;
		if(img!=null) {
			img2 = img.split("\\$");
			for (String data : img2) {
				System.out.println(data);
				list.add("/static/img/" + data);
			}
			}

		model.addAttribute("img", list);

		model.addAttribute("pagenum", pagenum);
		model.addAttribute("board_num", board_num);
		model.addAttribute("board", board);

		return "boardUpdate3";

	}

	@RequestMapping(value = "/boardForm2", method = RequestMethod.POST)
	public String boardForm2(@ModelAttribute Board3 board, Model model, HttpServletRequest request) {

		int parent = Integer.parseInt(request.getParameter("board_num"));
		int pagenum = Integer.parseInt(request.getParameter("pagenum"));
		model.addAttribute("parent", parent);
		model.addAttribute("pagenum", pagenum);

		return "boardForm3_re";

	}

	@RequestMapping(value = "insertboard", method = RequestMethod.POST)
	public String test(@RequestParam("img") MultipartFile[] img, @ModelAttribute Board3 board, Model model,
			HttpServletRequest request) {

		String path = "/uploadFiles/";

		String originFileName = null;

		String originFileName2 = null;

		String FileName = "";
		int count = 0;

		try {
			while (count < img.length && img.length != 1) {
				
				System.out.println(img.length);
				System.out.println(count);
				originFileName = img[count].getOriginalFilename() + "$";
				originFileName2 = img[count].getOriginalFilename();
				img[count].transferTo(new File(request.getServletContext().getRealPath(path)+originFileName2));

				FileName = FileName + originFileName;
				count++;
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int seq = boardService.getSeq3();

//		board.setBoard_img("/static/img/"+FileName);
		if(originFileName!=null) {
		board.setBoard_img(FileName);
		}
		board.setBoard_num(seq + 1);
		board.setBoard_parent(0);

		boardService.addBoard3(board);

		return "redirect:boardList/1";

		// String pageNum = request.getParameter("page");

	}

	@RequestMapping(value = "insertboard2", method = RequestMethod.POST)
	public String test2(@RequestParam("img") MultipartFile[] img, @ModelAttribute Board3 board, Model model,
			HttpServletRequest request) {

		
		File destinationFile;
		
		String path = "/uploadFiles/";

		String originFileName = null;

		String originFileName2 = null;

		
		String FileName = "";
		int count = 0;

		try {
			while (count < img.length && img.length != 1) {
				System.out.println(img.length);
				System.out.println(count);
				originFileName = img[count].getOriginalFilename() + "$";
				originFileName2 = img[count].getOriginalFilename();
				destinationFile = new File(request.getServletContext().getRealPath(path)+originFileName2);
				System.out.println(originFileName);
				//String safeFile = path + originFileName2;
				img[count].transferTo(destinationFile);

				FileName = FileName + originFileName;
				count++;
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		board.setBoard_img(FileName);

		int seq = boardService.getSeq3();

		board.setBoard_num(seq + 1);
		if(originFileName!=null) {
		}
		boardService.addBoard3(board);

		int pagenum = Integer.parseInt(request.getParameter("pagenum"));
		
		return "redirect:boardList/"+pagenum;

	}

	
	
	@RequestMapping(value = { "boardList/{pagenum}" })
	public String boardList(@PathVariable int pagenum, @ModelAttribute Board3 board, Model model,
			HttpServletRequest request) {
		model.addAttribute("pagenum", pagenum);
		
		
		List<Board3> list = boardService.boardList3();
		List<Board3> onePage = new ArrayList<Board3>();
		int listSize =0;
		
		if(list.size()%10==0&&list.size()!=0) {
			
		listSize = list.size() / 10;
		
		}else if(list.size()==0){
			listSize = 1;
			model.addAttribute("empty","empty");
			
		}else {
			listSize = list.size() / 10+1;
		}
		
		if (pagenum == 0) {
			pagenum = 1;
		} else {

			pagenum = pagenum * 10 - 10;
		}
		for (int i = pagenum; i < pagenum + 10; i++) {
			if (i == list.size()) {
				break;
			}
			onePage.add(list.get(i));

		}

		System.out.println(onePage);
		
		for (Board3 data : onePage) {
			if (data.getBoard_title().contains(" ")) {
				data.setBoard_title(data.getBoard_title().replace(" ", "&nbsp"));
			}
			if (data.getBoard_title().contains("re:")) {
				data.setBoard_title(
						data.getBoard_title().replace("re:", "<img src='https://i.imgur.com/M6vaIZf.gif'/> "));

			}
		}

		model.addAttribute("boardList", onePage);
		model.addAttribute("listSize", listSize);

		return "boardList3";
	}
	@RequestMapping(value = "boardUp" , method = RequestMethod.POST)
	public String boardUp(@ModelAttribute Board3 board, Model model,
			HttpServletRequest request) {
		List<Board3> list = boardService.boardList3();
		int pagenum = Integer.parseInt(request.getParameter("pagenum"));
		
		
		
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));	
		
		model.addAttribute("pagenum", pagenum);
		
		for(int i = 0; i<list.size(); i++) {
			if(list.get(i).getBoard_num()==board_num && i!=0) {
				return "redirect:boardView/"+list.get(i-1).getBoard_num();
			}
		}



		

		return "redirect:boardView/"+board_num;
	}
	@RequestMapping(value = "boardDown" , method = RequestMethod.POST)
	public String boardDown(@ModelAttribute Board3 board, Model model,
			HttpServletRequest request) {
		List<Board3> list = boardService.boardList3();
		int pagenum = Integer.parseInt(request.getParameter("pagenum"));
		
		
		
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));	
		
		model.addAttribute("pagenum", pagenum);
		
		for(int i = 0; i<list.size(); i++) {
			if(list.get(i).getBoard_num()==board_num && i!=list.size()-1) {
				return "redirect:boardView/"+list.get(i+1).getBoard_num();
			}
		}



		

		return "redirect:boardView/"+board_num;
	}
	@RequestMapping(value = {"boardView/{board_num}"} , method = RequestMethod.GET)
	public String boardView(@PathVariable int board_num, @ModelAttribute Board3 board, Model model, HttpServletRequest request) {

		List<Board3> list2 = boardService.boardList3();
		
		for(int i = 0; i<list2.size(); i++) {
			if(list2.get(i).getBoard_num()==board_num && i==0) {
				model.addAttribute("first","first");
			}else if(list2.get(i).getBoard_num()==board_num && i==list2.size()-1) {
				model.addAttribute("last","last");
			}
		}

		
		
		int pagenum = 0;
		
		List<Board3> pageSearch = boardService.boardList3();
		for(int i = 0; i<pageSearch.size(); i++) {
			if(pageSearch.get(i).getBoard_num()== board_num) {
				pagenum = i/10+1;
				break;
			}
		}
		
		

		
		board = boardService.boardView3(board_num);
		List<String> list = new ArrayList<String>();
		String img = board.getBoard_img();
		String[] img2;
		if(img!=null) {
		img2 = img.split("\\$");
		for (String data : img2) {
			System.out.println("img===="+data);
			list.add("uploadFiles/" + data);
		}
		}
		System.out.println(list);
		
		model.addAttribute("img", list);

		boardService.countUp3(board_num);

//		board = boardService.boardView(board_num);

		model.addAttribute("board", board);
		model.addAttribute("pagenum", pagenum);
		return "boardView3";

	}

	@RequestMapping(value = "updateBoard33")
	public String updateBoard(@RequestParam("img") MultipartFile[] img,@ModelAttribute Board3 board, Model model, HttpServletRequest request) {
		System.out.println(1111);
		
		int pagenum = Integer.parseInt(request.getParameter("pagenum"));

		
		

		File destinationFile;
		
		String path = "/uploadFiles/";

		String originFileName = null;

		String originFileName2 = null;

		
		String FileName = "";
		int count = 0;

		try {
			while (count < img.length) {
				System.out.println(img.length);
				System.out.println(count);
				originFileName = img[count].getOriginalFilename() + "$";
				originFileName2 = img[count].getOriginalFilename();
				destinationFile = new File(request.getServletContext().getRealPath(path)+originFileName2);
				System.out.println(originFileName);
				//String safeFile = path + originFileName2;
				img[count].transferTo(destinationFile);

				FileName = FileName + originFileName;
				count++;
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		board.setBoard_img(FileName);
		
		
		
		System.out.println(2222);
		

		System.out.println(3333);
		
		
		
		
		
		
		
		
		boardService.updateBoard33(board);
		model.addAttribute("board", board);
		model.addAttribute("pagenum", pagenum);
		return "redirect:boardList/"+pagenum;

	}
	@RequestMapping(value = "deleteBoard33" , method = RequestMethod.POST)
	public String deleteBoard(@ModelAttribute Board3 board, Model model, HttpServletRequest request) {
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int pagenum = Integer.parseInt(request.getParameter("pagenum"));

		boardService.deleteBoard3(board_num);

		List<Board3> list = boardService.boardList3();
		
		if(list.size()%10==0&&list.size()!=0) {
			
			pagenum = pagenum -1;
		
		}
		model.addAttribute("pagenum", pagenum);
		
		return "redirect:boardList/"+pagenum;

	}

}
