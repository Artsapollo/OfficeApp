create SCHEMA IF NOT EXISTS MA_STUDENT;

create sequence IF NOT EXISTS OFFICE_SEQ
	 MINVALUE 5555555555
	 MAXVALUE 9999999999
	 START with 5555555556
	INCREMENT BY 1;

drop table IF EXISTS MA_STUDENT.OFFICES;

 create TABLE MA_STUDENT.OFFICES(
 	OFFICE DECIMAL(38,0),
   CITY VARCHAR (0),
  REGION VARCHAR (0),
   TARGET DECIMAL(38,0),
   SALES DECIMAL (38,0)
   )