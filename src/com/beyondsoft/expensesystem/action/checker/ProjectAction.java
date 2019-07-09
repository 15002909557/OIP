package com.beyondsoft.expensesystem.action.checker;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.beyondsoft.expensesystem.dao.checker.GroupsDao;
import com.beyondsoft.expensesystem.dao.checker.ProjectDao;
import com.beyondsoft.expensesystem.dao.system.SysUserDao;
import com.beyondsoft.expensesystem.domain.checker.Groups;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.form.checker.ProjectForm;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;


@SuppressWarnings({ "unchecked", "deprecation" })
public class ProjectAction extends BaseDispatchAction
{
	 @SuppressWarnings("unused")
	private ServletConfig config;

	 final public void init(ServletConfig config) throws ServletException {
	  this.config = config;
	 }
	 /**
		 * ������Ŀ��Ϣ�б�
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 * author xiaofei / by collie 0427
		 * @flag
		 * hanxiaoyu01 2012-12-26 ȥ��rate��HP Manager
		 */
		public ActionForward downloadProjectList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
		{
			ProjectForm mainForm = (ProjectForm) form;
			Connection conn = null;
			List<Project> listProject = new ArrayList<Project>();
			List<Groups> listGroup = new ArrayList<Groups>();
			try
			{
				conn = DataTools.getConnection(request);
				String groupName = request.getParameter("groupName");
				String gid = request.getParameter("gid");
				//System.out.println(groupName); 
				System.out.println("gid="+gid);
				listProject=ProjectDao.getInstance().downloadProjectList(conn, groupName);
				System.out.println("listProject.size():"+listProject.size());
				if(groupName.equals("All Data"))
				{
					listGroup=GroupsDao.getInstance().downloadGroupList(conn, groupName);
				}
				else
				{
					Groups g = new Groups();
					g.setGname(groupName);
					listGroup.add(g);
				}
				String reportName="";
				reportName=reportName+"ProjectList";
				reportName=reportName+"_"+groupName;

				response.setHeader("Content-disposition","inline; filename="+reportName+".xls");
				response.setContentType("application/vnd.ms-excel");
				File file = null;
				file = new File(request.getSession().getServletContext().getRealPath("/documents/ProjectList.xls"));
			    FileInputStream fis = null;
			    
				//System.out.println("UploadProjectList filename:"+filename);
				fis = new FileInputStream(file);

				// dis.available() returns 0 if the file does not have more lines.
				HSSFWorkbook workbook = null;
				workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
				fis.close();
				
				
				HSSFCellStyle css = workbook.createCellStyle();
				css.setBorderBottom((short)1);
				css.setBorderLeft((short)1);
				css.setBorderRight((short)1);
				css.setBorderTop((short)1);
				
				HSSFSheet sheet = workbook.getSheet("Projects");
				//Set the excel file content
				
				//Project data
				//The first row
				
				int rowIndex=2;
				int recordIndex=0;
				HSSFRow row = sheet.createRow((short) rowIndex);

				//hanxiaoyu01 2012-12-227 ȥ�����õ��ֶ�
				if (listProject!=null){
					HSSFCell cell = row.createCell((short) 0);
					while (recordIndex<listProject.size()){
						
						row = sheet.createRow((short) rowIndex);
						
						cell = row.createCell((short) 0);
						cell.setCellValue(listProject.get(recordIndex).getProjectId());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 1);
						cell.setCellValue(listProject.get(recordIndex).getGroupName());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 2);
						cell.setCellValue(listProject.get(recordIndex).getUsername());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 3);
						cell.setCellValue(listProject.get(recordIndex).getComponent());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 4);
						cell.setCellValue(listProject.get(recordIndex).getProduct());
						cell.setCellStyle(css);
						//removed by dancy 20130608
						
						cell = row.createCell((short) 5);
						cell.setCellValue(listProject.get(recordIndex).getSkillLevel());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 6);
						cell.setCellValue(listProject.get(recordIndex).getLocation());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 7);
						cell.setCellValue(listProject.get(recordIndex).getOTType());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 8);
						cell.setCellValue(listProject.get(recordIndex).getWBS());
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 9);
						if(listProject.get(recordIndex).getConfirm()==1)
						{
							cell.setCellValue("Yes");
						}
						else
						{
							cell.setCellValue("No");
						}
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 10);
						if(listProject.get(recordIndex).getHidden()==1)
						{
							cell.setCellValue("Yes");
						}
						else
						{
							cell.setCellValue("No");
						}
						cell.setCellStyle(css);
						
						cell = row.createCell((short) 11);
						cell.setCellValue(listProject.get(recordIndex).getComments());
						cell.setCellStyle(css);
						
						recordIndex=recordIndex+1;
						rowIndex = rowIndex + 1;
					}
				}
				
				// dispose all the resources after using them.
				
				// output excel
				OutputStream ops=response.getOutputStream();
				workbook.write(ops);
				ops.flush();
		        ops.close();
		        workbook=null;
			}
			catch (Exception e)
			{
				// ���������Ϣ
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"errors.query"));
				this.saveErrors(request, errors);
				e.printStackTrace();
			}
			finally
			{
				if (conn != null)
				{
					conn.close();
				}
			}
			return mapping.findForward(mainForm.getOperPage());
		}
		
		/**
		 * �����û���Ϣ�б�
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 * author zhusimin
		 * @flag
		 * hanxiaoyu01 2012-12-26 employDate,PTOrate,workloadRate
		 */
		public ActionForward downloadUserList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
		{
			ProjectForm mainForm = (ProjectForm) form;
			Connection conn = null;
			@SuppressWarnings("unused")
			List<Project> listProject = new ArrayList<Project>();
			//List<SysUser> listUser = new ArrayList<SysUser>();
			List<SysUser> listUser2 = new ArrayList<SysUser>();
			List<Groups> listGroup = new ArrayList<Groups>();

			try
			{
				conn = DataTools.getConnection(request);
				String groupName = request.getParameter("groupName");
				@SuppressWarnings("unused")
				String scrollTop = request.getParameter("scrollTop");
				//System.out.println(groupName); 
				listProject=ProjectDao.getInstance().downloadProjectList(conn, groupName);
				
				listUser2 = SysUserDao.getInstance().downloadUserList2(conn, groupName);
				
				listGroup=GroupsDao.getInstance().downloadGroupList(conn, groupName);
				
				String reportName="";
				reportName=reportName+"UserList";
				reportName=reportName+"_"+groupName;

				response.setHeader("Content-disposition","inline; filename="+reportName+".xls");
				response.setContentType("application/vnd.ms-excel");
				File file = null;
				file = new File(request.getSession().getServletContext().getRealPath("/documents/UserList.xls"));
			    FileInputStream fis = null;
			    
				//System.out.println("UploadProjectList filename:"+filename);
				fis = new FileInputStream(file);

				// dis.available() returns 0 if the file does not have more lines.
				HSSFWorkbook workbook = null;
				workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
				fis.close();
				
				HSSFSheet sheet = workbook.getSheet("UserList");
				@SuppressWarnings("unused")
				HSSFSheet sheetUser = workbook.getSheet("ApproveLevelLookup");
				HSSFSheet sheetGroup = workbook.getSheet("GroupLookup");
				//Set the excel file content
				
				//Project data
				//The first row
				
				int rowIndex=0;
				int recordIndex=0;
				HSSFRow row = sheet.createRow((short) rowIndex);
				if (listUser2!=null){
					//Set the Title
					HSSFCell cell = row.createCell((short) 0);
					cell.setCellValue("UserId");
					cell = row.createCell((short) 1);
					cell.setCellValue("UserName");
					cell = row.createCell((short) 2);
					cell.setCellValue("Email");			
					cell = row.createCell((short) 3);
					cell.setCellValue("LevelId");
					cell = row.createCell((short) 4);
					cell.setCellValue("WorkloadGroupId");
					cell = row.createCell((short) 5);
					cell.setCellValue("GroupName");
					//cell = row.createCell((short) 6);
					//cell.setCellValue("EmployDate");
					//cell = row.createCell((short) 7);
					//cell.setCellValue("PTO rate");
					cell = row.createCell((short) 6);
					cell.setCellValue("ApproveLevel");
					cell = row.createCell((short) 7);
					cell.setCellValue("Rate");
					cell = row.createCell((short) 8);
					cell.setCellValue("HPEmployeeNumber");
					//Set the description
					rowIndex+=1;
					row = sheet.createRow((short) rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellValue("Do NOT change!");
					cell = row.createCell((short) 1);
					cell.setCellValue("Won't Save.");
					cell = row.createCell((short) 2);
					cell = row.createCell((short) 3);
					cell.setCellValue("Won't Save.");
					cell = row.createCell((short) 4);
					cell.setCellValue("Won't Save.");
					cell = row.createCell((short) 5);
					cell.setCellValue("Won't Save.");
					
					cell = row.createCell((short) 7);
					cell = row.createCell((short) 8);
					
					while (recordIndex<listUser2.size()){
						rowIndex=rowIndex+1;
						row = sheet.createRow((short) rowIndex);
						cell = row.createCell((short) 0);
						cell.setCellValue(listUser2.get(recordIndex).getUserId());
						cell = row.createCell((short) 1);
						cell.setCellValue(listUser2.get(recordIndex).getUserName());
						cell = row.createCell((short) 2);
						cell.setCellValue(listUser2.get(recordIndex).getEmail());			
						cell = row.createCell((short) 3);
						cell.setCellValue(listUser2.get(recordIndex).getLevelID());
						cell = row.createCell((short) 4);
						cell.setCellValue(listUser2.get(recordIndex).getWorkloadgroupID());
						cell = row.createCell((short) 5);
						cell.setCellValue(listUser2.get(recordIndex).getGroupName());
						
						/*cell = row.createCell((short) 6);
						
						if (null==listUser2.get(recordIndex).getEmploydate())
							cell.setCellValue("");
						else
						{
							cell.setCellValue((listUser2.get(recordIndex).getEmploydate().getYear()+1900) + "-" +(listUser2.get(recordIndex).getEmploydate().getMonth()+1) + "-" +listUser2.get(recordIndex).getEmploydate().getDate());
						}
						
						//
						cell = row.createCell((short) 7);
						cell.setCellValue(listUser2.get(recordIndex).getPTOrate());*/
						
						
						cell = row.createCell((short) 6);
						cell.setCellValue(listUser2.get(recordIndex).getApproveLevel());
						cell = row.createCell((short) 7);
						cell.setCellValue(listUser2.get(recordIndex).getWorkloadRate());
						cell = row.createCell((short) 8);
						cell.setCellValue(listUser2.get(recordIndex).getHPEmployeeNumber());
						
						recordIndex=recordIndex+1;
					}
					for (int i=0;i<8;i++){
						sheet.autoSizeColumn(i);
					}					
				}
				
				//Group data
				rowIndex=0;
				row = sheetGroup.createRow((short) rowIndex);

				if (listGroup!=null){
					HSSFCell cell = row.createCell((short) 0);
					cell.setCellValue("GroupId");
					cell = row.createCell((short) 1);
					cell.setCellValue("GroupName");
					
					while (rowIndex<listGroup.size()){
						rowIndex=rowIndex+1;
						row = sheetGroup.createRow((short) rowIndex);
						cell = row.createCell((short) 0);
						cell.setCellValue(listGroup.get(rowIndex-1).getGid());
						cell = row.createCell((short) 1);
						cell.setCellValue(listGroup.get(rowIndex-1).getGname());
					}
				}
				
				
				OutputStream ops=response.getOutputStream();
				workbook.write(ops);
				ops.flush();
		        ops.close();
		        workbook=null;
			}
			catch (Exception e)
			{
				// ���������Ϣ
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"errors.query"));
				this.saveErrors(request, errors);
				e.printStackTrace();
			}
			finally
			{
				if (conn != null)
				{
					conn.close();
				}
			}
			return mapping.findForward(mainForm.getOperPage());
		}
		
		/**
		 * �ϴ���Ŀ��Ϣ�б�
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 * author xiaofei / by collie 0427
		 * @flag
		 * hanxiaoyu01 2012-12-28 ȥ�����õ��ֶ�\
		 * remove PO Number column by dancy 20130608
		 */
		public ActionForward uploadProjectList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
		{
			ProjectForm mainForm = (ProjectForm) form;
			
			boolean re = false; // by collie 0520 �ϴ�ʧ��ʱ����ʾ
			
			Connection conn = null;
			Statement stmt = null;
			List<String> result = null;
			
			FormFile file = mainForm.getFiles();//ȡ���ϴ����ļ�
			InputStream stream = file.getInputStream();//���ļ�����
			/*String filename = (String) request.getParameter("ptxt");
			System.out.println("UploadProjectList filename:"+filename);
			InputStream stream = new FileInputStream(filename);
			*/
			try
			{
				conn = DataTools.getConnection(request);
				stmt = conn.createStatement();
				HSSFWorkbook workbook = null;
				workbook = new HSSFWorkbook(new POIFSFileSystem(stream));
				//fis.close();
				List<Project> listProject=new ArrayList<Project>();
				HSSFSheet sheet = workbook.getSheetAt(0);
				int rowIndex=0;
				//from the 3rd row
				rowIndex=2;
				HSSFRow row = sheet.getRow(rowIndex);
				while (null!=row){
					Project project=new Project();
					HSSFCell cell = row.getCell((short) 0);
					if (null!=cell){
						project.setProjectId((int) cell.getNumericCellValue());
					}
					
					cell = row.getCell((short) 1);
					if (null!=cell){
						project.setGroupName(cell.getStringCellValue());
					}
					
					cell = row.getCell((short) 2);
					if (null!=cell){
						project.setUsername(cell.getStringCellValue());
					}
					
					cell = row.getCell((short) 3);
					if (null!=cell){
						project.setComponent(cell.getStringCellValue());
					}
					cell = row.getCell((short) 4);
					if (null!=cell){
						project.setProduct(cell.getStringCellValue());
					}
					cell = row.getCell((short) 5);
					if (null!=cell){
						project.setSkillLevel(cell.getStringCellValue());
					}
					cell = row.getCell((short) 6);
					if (null!=cell){
						project.setLocation(cell.getStringCellValue());
					}
					cell = row.getCell((short) 7);
					if (null!=cell){
						project.setOTType(cell.getStringCellValue());
					}
					cell = row.getCell((short) 8);
					if (null!=cell){
						project.setWBS(cell.getStringCellValue());
					}
					
					cell = row.getCell((short) 9);
					if (null!=cell){
						String confirm=cell.getStringCellValue();
						if("Yes".equalsIgnoreCase(confirm)){
							project.setConfirm(1);
						}else{
							project.setConfirm(0);
						}
							
					}
					
					cell = row.getCell((short) 10);
					if (null!=cell){
						String hidden=cell.getStringCellValue();
						if("No".equalsIgnoreCase(hidden)){
							project.setHidden(0);
						}else{
							project.setHidden(1);
						}
					}
					
					cell = row.getCell((short) 11);
					if (null!=cell){
						project.setComments(cell.getStringCellValue());
					}
					listProject.add(project);
					rowIndex=rowIndex+1;
					row = sheet.getRow(rowIndex);
				}
			
				if (listProject.size()>0){
					result=ProjectDao.getInstance().uploadProjectList(conn, listProject);
				}
				
				re=true;// by collie 0520 �ϴ�ʧ��ʱ����ʾ
				// dispose all the resources after using them.
				//fis.close();
			}
			catch (Exception e)
			{
				// ���������Ϣ
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"errors.query"));
				this.saveErrors(request, errors);
				e.printStackTrace();
			}
			finally
			{
				if (stmt != null)
				{
					stmt.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			if (null!=result){
				if (result.size()>0){
					int index=0;
					@SuppressWarnings("unused")
					String projectListResult=null;
					while (index<result.size()){
						projectListResult=result.get(index);
						index=index+1;
					}
					request.setAttribute("projectListResult", result);
				}
			}
			// by collie 0520 �ϴ�ʧ��ʱ����ʾ
			if (re==false){
				request.setAttribute("uploadresult", "Upload is failed. Please check the Project List or contact the admin.");
			}else{
				request.setAttribute("uploadresult", "true");
			}
			return mapping.findForward(mainForm.getOperPage());
		}
		
		/**
		 * �ϴ��û���Ϣ�б�
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 * author zhusimin
		 * @flag
		 * hanxiaoyu01 2012-12-26 ȥ��ԭ����6��7��
		 */
		public ActionForward uploadUserList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
		{
			ProjectForm mainForm = (ProjectForm) form;
			
			boolean re = false; // by collie 0520 �ϴ�ʧ��ʱ����ʾ
			
			Connection conn = null;
			Statement stmt = null;
			List<String> result = null;
			
//			FormFile file = mainForm.getFiles();//ȡ���ϴ����ļ�
//			InputStream stream = file.getInputStream();//���ļ�����
			String filename = (String) request.getParameter("utxt");
			System.out.println("UploadUsertList filename:"+filename);
			InputStream stream = new FileInputStream(filename);
			
			try
			{
				conn = DataTools.getConnection(request);
				stmt = conn.createStatement();
				
				HSSFWorkbook workbook = null;
				workbook = new HSSFWorkbook(new POIFSFileSystem(stream));

				List<SysUser> listUser=new ArrayList<SysUser>();
				
				HSSFSheet sheet = workbook.getSheetAt(0);
				
				int rowIndex=0;
				
				rowIndex=2;
				HSSFRow row = sheet.getRow(rowIndex);
				while (null!=row && null!=row.getCell((short) 0)){
					//System.out.println(row.getCell(0));
					SysUser sysUser=new SysUser();
					//read the User information
					HSSFCell cell = row.getCell((short) 0);
					if (null!=cell){
						sysUser.setUserId((int) cell.getNumericCellValue());
					}
					cell = row.getCell((short) 1);
					cell = row.getCell((short) 2);
					if (null!=cell){
						sysUser.setEmail(cell.getStringCellValue());
						System.out.println("setEmail="+cell.getStringCellValue());
					}
					/*cell = row.getCell((short) 6);
					
					if (null!=cell){
						//1 ��ʾ ��ɵģ�δ���
						//0 ��ʾ ��Ĺ�� ��Ч
						//System.out.println("setEmploydate cell.getCellType():"+cell.getCellType());
						
						String strDate = cell.getStringCellValue();
						//System.out.println("strDate in action "+strDate);
						
						if (null==strDate) {
							sysUser.setEmploydate(null);
						}else if("".equals(strDate)){
							sysUser.setEmploydate(null);
						}else{
							
							sysUser.setEmployDate2(strDate);
						}
					}
					
					//
					cell = row.getCell((short) 7);
					if (null!=cell){
						System.out.println("setPTOrate="+cell.getNumericCellValue());
						sysUser.setPTOrate((int) cell.getNumericCellValue());
					}
					//
*/						
					cell = row.getCell((short) 6);
					if (null!=cell){
						sysUser.setApproveLevel((int) cell.getNumericCellValue());
					}
					cell = row.getCell((short) 7);
					if (null!=cell){
						sysUser.setWorkloadRate((double) cell.getNumericCellValue());
					}
					
					cell = row.getCell((short) 8);
					if (null!=cell){
						//1 ��ʾ ��ɵģ�δ���
						//0 ��ʾ ��Ĺ�� ��Ч
						if (cell.getCellType()==0)
						{
							sysUser.setHPEmployeeNumber(Integer.toString((int) cell.getNumericCellValue()));
						}else
						{
							sysUser.setHPEmployeeNumber(cell.getStringCellValue());
						}						
					}else{
						sysUser.setHPEmployeeNumber("");
					}
					
					listUser.add(sysUser);
					rowIndex=rowIndex+1;
					row = sheet.getRow(rowIndex);
				}
				
				if (listUser.size()>0){
					//update the projects with the listProject
					result=SysUserDao.getInstance().uploadUserList(conn, listUser);
				}
				
				re=true;// by collie 0520 �ϴ�ʧ��ʱ����ʾ
				// dispose all the resources after using them.
				//fis.close();
			}
			catch (Exception e)
			{
				// ���������Ϣ
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"errors.query"));
				this.saveErrors(request, errors);
				e.printStackTrace();
			}
			finally
			{
				if (stmt != null)
				{
					stmt.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			if (null!=result){
				if (result.size()>0){
					int index=0;
					@SuppressWarnings("unused")
					String userListResult=null;
					while (index<result.size()){
						userListResult=result.get(index);
						index=index+1;
					}
					request.setAttribute("userListResult", result);
				}
			}
			// by collie 0520 �ϴ�ʧ��ʱ����ʾ
			if (re==false){
				request.setAttribute("uploadresult", "Upload is failed. Please check the User List or contact the admin.");
			}else{
				request.setAttribute("uploadresult", "true");
			}
			return mapping.findForward(mainForm.getOperPage());
		}
	
	
	/**
	 * ��ѯ��Ŀ�б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		List projectList = null;
		List<String> dateList = new ArrayList<String>();
		Connection conn = null;
		@SuppressWarnings("unused")
		Statement stmt = null;
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			projectList = ProjectDao.getInstance().searchProject(conn,mainForm.getPageModel());
		}
		catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		dateList.add(0, mainForm.getStartdate().replace('/', '-').substring(5));
		for(int i = 0; i < ((Date.parse(mainForm.getEnddate())-Date.parse(mainForm.getStartdate()))/(60 * 1000 * 60 * 24));i++)
		{
			String str1 = new Date(Date.parse(mainForm.getStartdate()) + (i+1) * 60 * 1000 * 60 * 24).toLocaleString().replace(' ', '-');
			String[] str2 = str1.split("-");
			String month = str2[1];
			month = Integer.parseInt(month) + 100 + "";
			month = month.substring(1);
			String day = str2[2];
			day = Integer.parseInt(day) + 100 + "";
			day = day.substring(1);
			dateList.add(i+1, month + "-" + day);
		}
		request.setAttribute("projectList", projectList);
		request.setAttribute("dateList", dateList);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ���³�ʼ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		Connection conn = null;
		Statement stmt = null;
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			mainForm.setProject(ProjectDao.getInstance().load(stmt,
					Integer.parseInt(mainForm.getRecid())));
			mainForm.setComments(mainForm.getProject().getComments());
		}
		catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ������Ŀ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			ProjectDao.getInstance().saveProject(conn, mainForm.getProject(),
					mainForm);
		}
		catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		if (mainForm.getErrors().isEmpty()
				&& mainForm.getStrErrors().length() == 0)
		{
			mainForm.setStrErrors("true");
		}
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ������Ŀ�ֶ��б� ProjectName, ProductName, SkillLevel, Location, OTType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @flag
	 */
	public ActionForward configProjectAttribute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;		
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ������Ŀ�ֶ��б� ProjectName
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addProjectName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String pname = (String) request.getParameter("pname");
		String fwsw = (String) request.getParameter("fwsw0");
		System.out.println("add new project:"+pname+" into "+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addProjectName(conn, pname, Integer.parseInt(fwsw));
			System.out.println("add new projectname:"+pname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "project");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� ProjectName
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyProjectName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String projectid = (String) request.getParameter("pjid");
		String projectname = (String) request.getParameter("pj"+projectid+"I");
		//System.out.println("modify project id-"+projectid+" name:"+projectname);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyProjectName(conn, projectname, Integer.parseInt(projectid));
			System.out.println("update projectname:"+projectname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "pj"+projectid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� ProjectName
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeProjectName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String projectid = (String) request.getParameter("pjid");
		System.out.println("remove projectid:"+projectid);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeProjectName(conn, Integer.parseInt(projectid));
			System.out.println("remove projectID:"+projectid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "pj"+projectid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ������Ŀ�ֶ��б� ProductName
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addProductName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String pdname = (String) request.getParameter("pdname");
		String fwsw = (String) request.getParameter("fwsw1");
		System.out.println("pdname="+pdname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addProductName(conn, pdname, Integer.parseInt(fwsw));
			System.out.println("add new productname:"+pdname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "product");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� ProductName
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyProductName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String productid = (String) request.getParameter("pdid");
		String productname = (String) request.getParameter("pd"+productid+"I");
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyProductName(conn, productname, Integer.parseInt(productid));
			System.out.println("modify productname:"+productname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "pd"+productid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� ProductName
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeProductName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String productid = (String) request.getParameter("pdid");
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeProductName(conn, Integer.parseInt(productid));
			System.out.println("remove productname:"+productid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "pd"+productid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ������Ŀ�ֶ��б� SkillLevel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSkillLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String skillname = (String) request.getParameter("skillname");
		String shortname = (String) request.getParameter("skillshortname");
		String fwsw = (String) request.getParameter("fwsw2");
		System.out.println("skillname="+skillname+" shortname="+shortname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addSkillLevel(conn, skillname, shortname, Integer.parseInt(fwsw));
			System.out.println("add new skillname:"+skillname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "skill");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� SkillLevel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifySkillLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String skillid = (String) request.getParameter("sklid");
		String skillname = (String) request.getParameter("skl"+skillid+"I");
		String shortname = (String) request.getParameter("skls"+skillid+"I");
		//System.out.println("modify-"+skillid+" skillname="+skillname+" shortname="+shortname);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifySkillLevel(conn, skillname, shortname, Integer.parseInt(skillid));
			System.out.println("modify skillname:"+skillname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "skl"+skillid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� SkillLevel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeSkillLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String skillid = (String) request.getParameter("sklid");
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeSkillLevel(conn, Integer.parseInt(skillid));
			System.out.println("remove skillid:"+skillid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "skl"+skillid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ������Ŀ�ֶ��б� Location
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String locationname = (String) request.getParameter("locationname");
		String fwsw = (String) request.getParameter("fwsw3");
		System.out.println("locationname="+locationname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addLocation(conn, locationname, Integer.parseInt(fwsw));
			System.out.println("add new locationname:"+locationname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "location");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� Location
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String locationid = (String) request.getParameter("loid");
		String locationname = (String) request.getParameter("lo"+locationid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyLocation(conn, locationname, Integer.parseInt(locationid));
			System.out.println("add new locationname:"+locationname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "lo"+locationid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� Location
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String locationid = (String) request.getParameter("loid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeLocation(conn, Integer.parseInt(locationid));
			System.out.println("remove locationid:"+locationid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "lo"+locationid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ������Ŀ�ֶ��б� OTType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOTType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String otname = (String) request.getParameter("otname");
		String fwsw = (String) request.getParameter("fwsw4");
		System.out.println("otname="+otname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addOTType(conn, otname, Integer.parseInt(fwsw));
			System.out.println("add new otname:"+otname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "ottype");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� OTType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyOTType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String otid = (String) request.getParameter("otid");
		String otname = (String) request.getParameter("ot"+otid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyOTType(conn, otname, Integer.parseInt(otid));
			System.out.println("add new otname:"+otname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ot"+otid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� OTType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeOTType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String otid = (String) request.getParameter("otid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeOTType(conn, Integer.parseInt(otid));
			System.out.println("remove otid:"+otid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ot"+otid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	/*��һ����ͳͳû��ע�Ͱ���������������Ӧ�þ����˰� ����Ҫע�Ͱ� Ϊ����˼һ�� ���ǼӼ���ɣ�
	 *  �������� �������ʱ��ܲ�˳ ����Ҳ�� �� ����
	 */
	/**
	 * ������Ŀ�ֶ��б� WorkType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addWorkType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String wtname = (String) request.getParameter("wtname");
		String fwsw = (String) request.getParameter("fwsw6");
		System.out.println("wtname="+wtname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addWorkType(conn, wtname, Integer.parseInt(fwsw));
			System.out.println("add new wtname:"+wtname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "worktype");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� WorkType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyWorkType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String worktypeid = (String) request.getParameter("wtid");
		String worktypename = (String) request.getParameter("wt"+worktypeid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyWorkType(conn, worktypename, Integer.parseInt(worktypeid));
			System.out.println("modify worktypename:"+worktypename+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "wt"+worktypeid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� WorkType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeWorkType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String worktypeid = (String) request.getParameter("wtid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeWorkType(conn, Integer.parseInt(worktypeid));
			System.out.println("remove worktypeid:"+worktypeid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "wt"+worktypeid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ������Ŀ�ֶ��б� Milestone
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMilestone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String milestone = (String) request.getParameter("msname");
		String fwsw = (String) request.getParameter("fwsw7");
		System.out.println("milestone="+milestone+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addMilestone(conn, milestone, Integer.parseInt(fwsw));
			System.out.println("add new milestone:"+milestone+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "milestone");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� Milestone
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyMilestone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String milestoneid = (String) request.getParameter("msid");
		String milestonename = (String) request.getParameter("ms"+milestoneid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyMilestone(conn, milestonename, Integer.parseInt(milestoneid));
			System.out.println("modify milestonename:"+milestonename+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ms"+milestoneid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� Milestone
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeMilestone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String milestoneid = (String) request.getParameter("msid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeMilestone(conn, Integer.parseInt(milestoneid));
			System.out.println("remove milestoneid:"+milestoneid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ms"+milestoneid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ������Ŀ�ֶ��б� TestType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTestType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String testtypename = (String) request.getParameter("ttname");
		String fwsw = (String) request.getParameter("fwsw8");
		System.out.println("testtypename="+testtypename+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addTestType(conn, testtypename, Integer.parseInt(fwsw));
			System.out.println("add new testtypename:"+testtypename+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "testtype");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� TestType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyTestType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String testtypeid = (String) request.getParameter("ttid");
		String testtypename = (String) request.getParameter("tt"+testtypeid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyTestType(conn, testtypename, Integer.parseInt(testtypeid));
			System.out.println("modify testtypeid:"+testtypeid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "tt"+testtypeid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� TestType
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTestType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String testtypeid = (String) request.getParameter("ttid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeTestType(conn, Integer.parseInt(testtypeid));
			System.out.println("remove testtypeid:"+testtypeid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "tt"+testtypeid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ������Ŀ�ֶ��б� TestSession
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTestSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String testsessionname = (String) request.getParameter("tsname");
		String fwsw = (String) request.getParameter("fwsw9");
		System.out.println("add testsessionname="+testsessionname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addTestSession(conn, testsessionname, Integer.parseInt(fwsw));
			System.out.println("add new testsessionname:"+testsessionname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "testsession");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� TestSession
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyTestSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String testsessionid = (String) request.getParameter("tsid");
		String testsessionname = (String) request.getParameter("ts"+testsessionid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyTestSession(conn, testsessionname, Integer.parseInt(testsessionid));
			System.out.println("modify testsessionid:"+testsessionid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ts"+testsessionid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� TestSession
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTestSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String testsessionid = (String) request.getParameter("tsid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeTestSession(conn, Integer.parseInt(testsessionid));
			System.out.println("remove testsessionid:"+testsessionid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ts"+testsessionid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ������Ŀ�ֶ��б� Description
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String descriptionname = (String) request.getParameter("dename");
		String fwsw = (String) request.getParameter("fwsw5");
		System.out.println("add descriptionname="+descriptionname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addDecription(conn, descriptionname, Integer.parseInt(fwsw));
			System.out.println("add new descriptionname:"+descriptionname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "description");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� Description
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String descriptionid = (String) request.getParameter("deid");
		String descriptionname = (String) request.getParameter("de"+descriptionid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyDecription(conn, descriptionname, Integer.parseInt(descriptionid));
			System.out.println("modify descriptionname:"+descriptionname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "de"+descriptionid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� Description
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String descriptionid = (String) request.getParameter("deid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeDecription(conn, Integer.parseInt(descriptionid));
			System.out.println("remove descriptionid:"+descriptionid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "de"+descriptionid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ������Ŀ�ֶ��б� SkillCategory
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSkillCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String skillcategoryname = (String) request.getParameter("scname");
		String fwsw = (String) request.getParameter("fwsw9");
		System.out.println("add skillcategoryname="+skillcategoryname+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().addSkillCategory(conn, skillcategoryname, Integer.parseInt(fwsw));
			System.out.println("add new skillcategoryname:"+skillcategoryname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "skillcategory");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸���Ŀ�ֶ��б� SkillCategory
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifySkillCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String skillcategoryid = (String) request.getParameter("scid");
		String skillcategoryname = (String) request.getParameter("sc"+skillcategoryid+"I");
		System.out.println("modify skillcategoryname:"+skillcategoryname+" id="+skillcategoryid);

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifySkillCategory(conn, skillcategoryname, Integer.parseInt(skillcategoryid));
			System.out.println("modify skillcategoryname:"+skillcategoryname+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "sc"+skillcategoryid);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ����Ŀ�ֶ��б� SkillCategory
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeSkillCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProjectForm mainForm = (ProjectForm) form;
		String skillcategoryid = (String) request.getParameter("scid");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().removeSkillCategory(conn, Integer.parseInt(skillcategoryid));
			System.out.println("remove skillcategoryid:"+skillcategoryid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "sc"+skillcategoryid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	
}
