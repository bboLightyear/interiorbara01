package com.tech.ibara.csnotice.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tech.ibara.csnotice.dto.QnaDto;
import com.tech.ibara.csnotice.dto.QnaImgDto;
import com.tech.ibara.csnotice.dto.QnaReplyDto;

public interface QnaBoardIDao {

	public ArrayList<QnaDto> qnalist(int rowStart, int rowEnd, String searchKeyword, String string);

	public int selectBoardTotalCount1();
	public int selectBoardTotalCount2();
	public int selectBoardTotalCount3();
	public int selectBoardTotalCount4();
	public int selectBoardTotalCount5();
	public int selectBoardTotalCount6();

	public Integer selsnbno();

	public void qnawrite(String nbwrite, String nbtitle, String nbcontent, int snbno, String qnadiv);

	public void imgwrite(int snbno, String changeFile);

	public void uphit(String nbno);

	public QnaDto qnacontent(String nbno);

	public QnaDto qnacontentview(String nbno);

	public int selfilecode(String nbno);

	public void imgdelete(int filecode);

	public void qnadelete(String nbno);

	public ArrayList<QnaImgDto> qnacontentimgview(String nbno);

	public void qnareply(String nbno, String qnareply, String qnarewriter);

	public ArrayList<QnaReplyDto> replylist(String nbno);

	public ArrayList<QnaReplyDto> replyrlist(String rnbno);

	public Integer replycnt(String nbno);

	public void qnareply_r(String nbno, String rnbno, String rwriter, String rcontent, String rnbgroup, String rnbstep, String rnbindent);

	public void replyShape(String rnbgroup, String rnbstep);

	public void qnaeditproc(String nbno, String nbtitle, String nbcontent, String qnadiv);

	public ArrayList<String> getfileListbefore(String nbno);

	public void deletefilebefore(String nbno);

	public void editimg(int snbno, String changeFile);

	
}
