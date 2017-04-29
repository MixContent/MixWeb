package com.dugu.spring.mvc.blob.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dugu.spring.mvc.blob.dao.DocumentDAO;
import com.dugu.spring.mvc.blob.model.Document;

@Controller
@MultipartConfig
public class DocumentController {

	@Autowired
	private DocumentDAO documentDao;

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = "content-type=multipart/*")
	public String save(@ModelAttribute("document") Document document,
			@RequestParam("myFile") MultipartFile myFile, Model model) {

		System.out.println("Name:" + document.getName());
		System.out.println("Desc:" + document.getDescription());
		System.out.println("File:" + myFile.getName());
		System.out.println("ContentType:" + myFile.getContentType());

		try {
			Blob blob = Hibernate.getLobCreator(
					documentDao.getSessionFactory().openSession()).createBlob(
					myFile.getInputStream(), 0);
			document.setFilename(myFile.getOriginalFilename());
			document.setContent(blob);
			document.setContentType(myFile.getContentType());
			document.setCreated(new java.sql.Date(new Date().getTime()));
			documentDao.save(document);
			model.addAttribute("message", "file" + document.getFilename()
					+ " Uploaded successfully..");
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}

		return "documents";
	}

	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public void displayImage(Model model, @RequestParam("id") String id,
			HttpServletResponse response) throws IOException, SQLException {
		Document document = documentDao.getImage(Integer.valueOf(id));
		Blob blobData = document.getContent();
		int blobLength = (int) blobData.length();
		byte[] thumb = blobData.getBytes(1, blobLength);

		String name = "demo";
		response.setContentType("image/jpeg");
		response.setContentLength(thumb.length);

		response.setHeader("Content-Disposition", "inline; filename=\"" + name
				+ "\"");

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(new ByteArrayInputStream(thumb));
			output = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[8192];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} catch (IOException e) {
			System.out
					.println("There are errors in reading/writing image stream "
							+ e.getMessage());
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException ignore) {
				}
			if (input != null)
				try {

					input.close();
				} catch (IOException ignore) {
				}
		}

	}
}
