PROJECT OVERVIEW_
		This student management System created and developed for education institution to track and
		manage students attendance.i used Layered architecture to develop this system.main layers are 
		dto layer/dao layer/service layer and ui layer.
		i have to coded 7 dtos,6 daos,6 service,3 uis and database connectivity.
		i used pure Java/JDBC/JavaFX and MySQL  as tecnologies.admin can control full system and lecturer
		can mark attendance & view reports.
		main functions are Role based login,CRUD operation,Mark attendance,generate attendance report.

		
		

	ams PACKAGE_
		ENTRY-
		       	AMSApplication{AMSApplication.java}

		DATABASE CONFIGURATION-			
			config{DBConfig.java}

                DATA TRANSFER OBJECT LAYER-
			dto(model){user/course/student/lecture/classSchedule/attendance}

		DATA ACCESS OBJECT LAYER-
			dao{userDAO/courseDAO/studentDAO/lecturerDAO/classScheduleDAO/attendanceDAO}
			

		SERVICE LAYER-
			service{userService/courseService/studentService/lecurerService/classScheduleService/attendanceService}
			

		PRESENTATION LAYER(USER INTERFACE)-
			ui{LoginUI/AdminDashboardUI/LecturerDashboardUI}


TOOLS & TECHNOLOGIES:
		NETBEAM IDE/PURE JAVA/MYSQL/JDBC/JAVAFX/GIT & GITHUB

USES:
		Layered Architecture


LOGIN CREDENTIALS:
			Admin:
				name:admin
				password:admin123
			Lecturer:
				name:lecturer1
				password:lect123


		



		
		
