drop table `04`.`Apps`;

drop table `04`.`AuthUsers`;

drop table `04`.`Department`;

drop table `04`.`HasDepartment`;

drop table `04`.`HasGuardian`;

drop table `04`.`HasLink`;

drop table `04`.`HasMedia`;

drop table `04`.`HasSubDepartment`;

drop table `04`.`HasTag`;

drop table `04`.`ListOfApps`;

drop table `04`.`Media`;

drop table `04`.`MediaDepartmentAccess`;

drop table `04`.`MediaProfileAccess`;

drop table `04`.`Profile`;

drop table `04`.`Tags`;



CREATE  TABLE `04`.`AuthUsers` (

  `certificate` VARCHAR(512) NOT NULL ,

  `idUser` INT NOT NULL AUTO_INCREMENT,
  
  `aRole` INT NOT NULL,
    
  `username` VARCHAR(45) UNIQUE NOT NULL,
  
  `password` VARCHAR(45) NOT NULL,

  PRIMARY KEY (`certificate`),

  UNIQUE INDEX `idUser_UNIQUE` (`idUser` ASC) );


CREATE  TABLE `04`.`Apps` (

  `idApp` INT NOT NULL AUTO_INCREMENT,

  `name` VARCHAR(45) NOT NULL ,

  `version` VARCHAR(45) NOT NULL ,
  
  `icon` VARCHAR(45) NOT NULL,
  
  `package` VARCHAR(45) NOT NULL,
  
  `activity` VARCHAR(45) NOT NULL,

  PRIMARY KEY (`idApp`) );

CREATE  TABLE `04`.`Tags` (

  `idTags` INT NOT NULL AUTO_INCREMENT,

  `caption` VARCHAR(45) NOT NULL ,

  PRIMARY KEY (`idTags`) ,

  UNIQUE INDEX `caption_UNIQUE` (`caption` ASC) );

CREATE  TABLE `04`.`Profile` (

  `idProfile` INT NOT NULL ,

  `firstname` VARCHAR(45) NOT NULL ,

  `surname` VARCHAR(45) NOT NULL ,

  `middlename` VARCHAR(45) NULL ,

  `pRole` INT NOT NULL ,

  `phone` BIGINT NOT NULL ,

  `picture` VARCHAR(45) NULL ,

  `settings` BLOB NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`AuthUsers` (`idUser` ) on delete cascade,

  PRIMARY KEY (`idProfile`) );

CREATE  TABLE `04`.`ListOfApps` (

  `idApp` INT NOT NULL ,

  `idProfile` INT NOT NULL ,
  
  `setting` BLOB,
  
  `stats` BLOB,
  
  FOREIGN KEY (`idApp` )

  REFERENCES `04`.`Apps` (`idApp` ),
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ) ON DELETE CASCADE,

  PRIMARY KEY (`idApp`,`idProfile`) );

CREATE  TABLE `04`.`Department` (

  `idDepartment` INT NOT NULL ,

  `name` VARCHAR(45) NOT NULL ,

  `address` VARCHAR(45) NOT NULL ,

  `phone` BIGINT NOT NULL ,

  `email` VARCHAR(45) NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`AuthUsers` (`idUser` ),

  PRIMARY KEY (`idDepartment`) );

CREATE  TABLE `04`.`HasDepartment` (

  `idProfile` INT NOT NULL ,

  `idDepartment` INT NOT NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ) ON DELETE CASCADE,

  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),


  PRIMARY KEY (`idProfile`, `idDepartment`) );

CREATE  TABLE `04`.`HasGuardian` (

  `idGuardian` INT NOT NULL ,

  `idChild` INT NOT NULL ,
  
  FOREIGN KEY (`idGuardian` )

  REFERENCES `04`.`Profile` (`idProfile` ) on delete cascade,
  
  FOREIGN KEY (`idChild` )

  REFERENCES `04`.`Profile` (`idProfile` ) on delete cascade,

  PRIMARY KEY (`idGuardian`,`idChild`) );

CREATE  TABLE `04`.`HasSubDepartment` (

  `idDepartment` INT NOT NULL ,

  `idSubDepartment` INT NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),
  
  FOREIGN KEY (`idSubDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),

  PRIMARY KEY (`idDepartment`, `idSubDepartment`) );

CREATE  TABLE `04`.`Media` (

  `idMedia` INT NOT NULL AUTO_INCREMENT,

  `mPath` VARCHAR(45) NOT NULL ,

  `name` VARCHAR(45) NOT NULL ,

  `mPublic` TINYINT NOT NULL ,

  `mType` VARCHAR(45) NOT NULL ,

  `ownerID` INT NOT NULL ,
  
  FOREIGN KEY (`OwnerID` )

  REFERENCES `04`.`AuthUsers` (`idUser` )  ON DELETE CASCADE,

  PRIMARY KEY (`idMedia`) );

CREATE  TABLE `04`.`HasTag` (

  `idMedia` INT NOT NULL ,

  `idTag` INT NOT NULL ,
  
  FOREIGN KEY (`idMedia` )

  REFERENCES `04`.`Media` (`idMedia` ) on delete cascade,
  
  FOREIGN KEY (`idTag` )

  REFERENCES `04`.`Tags` (`idTags` ),

  PRIMARY KEY (`idMedia`, `idTag`) );

CREATE  TABLE `04`.`HasLink` (

  `idMedia` INT NOT NULL ,

  `idSubMedia` INT NOT NULL ,
  
  FOREIGN KEY (`idMedia` )

  REFERENCES `04`.`Media` (`idMedia` ) on delete cascade,
  
  FOREIGN KEY (`idSubMedia` )

  REFERENCES `04`.`Media` (`idMedia` ) on delete cascade,

  PRIMARY KEY (`idMedia`, `idSubMedia`) );

CREATE  TABLE `04`.`MediaProfileAccess` (

  `idProfile` INT NOT NULL ,

  `idMedia` INT NOT NULL ,
  
  FOREIGN KEY (`idProfile` )

  REFERENCES `04`.`Profile` (`idProfile` ) ON DELETE CASCADE,
  
  FOREIGN KEY (`idMedia` )

  REFERENCES `04`.`Media` (`idMedia` ) ON DELETE CASCADE,

  PRIMARY KEY (`idProfile`, `idMedia`) );

CREATE  TABLE `04`.`MediaDepartmentAccess` (

  `idDepartment` INT NOT NULL ,

  `idMedia` INT NOT NULL ,
  
  FOREIGN KEY (`idDepartment` )

  REFERENCES `04`.`Department` (`idDepartment` ),
  
  FOREIGN KEY (`idMedia` )

  REFERENCES `04`.`Media` (`idMedia` ),

  PRIMARY KEY (`idDepartment`, `idMedia`) );

insert into Apps values(1,'TestAppe1','1.0.0', 'null', 'null', 'null');
insert into Apps values(2,'TestAppe2','1.0.0', 'null', 'null', 'null');

/* Users id=1 - 10 */
insert into AuthUsers values ('jkkxlagqyrztlrexhzofekyzrnppajeobqxcmunkqhsbrgpxdtqgygnmbhrgnpphaxsjshlpupgakmirhpyfaivvtpynqarxsghhilhkqvpelpreevykxurtppcggkzfaepihlodgznrmbrzgqucstflhmndibuymmvwauvdlyqnnlxkurinuypmqypspmkqavuhfwsh',null,0,'User01','User01');
insert into AuthUsers values ('ldsgjrtvuiwclpjtuxysmyjgpzsqnrtbwbdmgpalnlwtxzdubhbbtkioukaiwgbebhwovfuevykgbbnktnbzhxwugnkkllgjyovisfzzghyuqvxaoscblwqtvujqzgctslihoqetymxfupblcegpfjrfzyrfnjwevgeimxkrdixocyqmaxmyelptofyrsrtrggffmgak',null,0,'User02','User02');
insert into AuthUsers values ('atntrqkxlnftvxquwrwcdloaumdhzkgyglezzsebpvnethrlstvmlorrolymdynjcyonkrtvcuagwigdqqkftsxxhklcnbhznthcqjxnjzzdoqvmfdlxrudcyakvrnfcbohdumawlwmfndjascmvrsoxfjgwzhdvcvqcroxoyjeazmxtrjtlkldoevgdrqvgfbklhtgm',null,0,'User03','User03');
insert into AuthUsers values ('juuaygujymvacvldvhgirtvtumbdtfhmthtumpgqjvlhzvpzmwezifupvfhpjermlckxdvmjfpmqfadepwdvgdtwvoqkruyeuklsrurgirioqiqdzdxnbuemdmezycyncjqkvcjhgfusfggckxaispgbrzcxmtrgztnbshucxpaoodjvqujhwyeccnsxfjgkrjfoszvu',null,0,'User04','User04');
insert into AuthUsers values ('qyrmohbmcnsljhknvggvcdifarowqvpckzxfvlkwnglztjormumiroifttxbqzmybyvbulrvnoxrdidieoxeeayxkohqrwapdnszdnnegsgdnwdoenjlwcgurjtvmufwhjfnkcpyalzkrvzmspdliaodnlkookaszjyurwjclxufomktgucbsaknxztrpkhxutbelrrc',null,0,'User05','User05');
insert into AuthUsers values ('bphiomxvbsricewxcpuzpdtqjdcywlaplsmzjqzayhdyxeawyaeeofkpvfhwaudzwaafihtfuddsbrjhuztepopztbdgcokafnrgqrbaydsryfianltscyitukssklazgubhkdvvjqolmwiyyhuidhyqxoxwabmvdnnxatvzhvkawyiktbswjdcqlustzermuytgqvae',null,0,'User06','User06');
insert into AuthUsers values ('qldstjxxvbdacxfqjfwbjysjzmuobkajrdnofbtewuwpfkrobhqeblvpolnwtrhxiovuepqgssemakkjvpqoworokauseymbhafvmyhcnpdfxvpevsnjvbcwzlbordoaifgjixztsadmhldzbnvbaaxvmhssijnhvrqfretxqxhxvxsjuwcknxbktfigctbwppndwxpj',null,0,'User07','User07');
insert into AuthUsers values ('duzogdegzhtazsqmjwmxfktmnqcbpuxuvgxmbhpkzcnomoxrtrqlfisqdvfmnhmrmssocxifquqtfnzczzznunywesepobaiikgzlaecairmrlcqzdtfrxmispgamrwwcgzvlfnaysrexwdtmhytgpnncelikvrfozixdtsixwipnfactxywyeqvjhosqwekdnbcbcac',null,0,'User08','User08');
insert into AuthUsers values ('wzkbaaogonrkgckgfrjrwdvklpcwpmloamhlfqmytotpqkrixkwqnqamazcbybhjfdalsvqpdpiwlyctcuvtyclgreonxkqqevokjdbjwdcrgkhozleidpnoiwkdmcaylmosmwbfsrcnmlwlstgfljfwdgodinjrjrygeurrxyjpudsqukqkdgwwerlotgafhqhlxszv',null,0,'User09','User09');
insert into AuthUsers values ('osrvixzwyklicmkcwymiccawlbgctvigycafvftciuznhqrrztnyoafqrfuskqdbddrrppnadthngsfsdvooybjfwdfcdzxfdpzyvaxibxcbqnebgifdusgldvdkeonkvdmcmwffghreolhfxhrwgcogpsfayzxsoeyqwddposjdqwwiovnwabefudybzapihunhluaj',null,0,'User10','User10');
/* Dept id=11 - 14 */
insert into AuthUsers values ('cbtjtjbzzfqtsdjcosdudmssfwgnfyfrcpkffxzqxkgvypdkgreaaluctwpjpfyiteismfqtinhcdfxtpmggieevbnawmlfvkykfcmfwblxwfzozdxcdqifuahshoyhsuigdnhzexuaaoqwsfyalxgsvwktdqqjfuaxfgebgkwhghtkkjmdtaxquxmoqfjaibehimitsk',null,0,'Dep01','Dep01');
insert into AuthUsers values ('uawkenuccikpredvjqzvstrxhrajeygolljxoylbryhzytglxgldfnzeoybifokrdbdvkuscqfoutwemarihovwzibzrfmiihmevjfblrclcnrqmerghcddmdvaulyosrjyyebtrlgiqjquwghzsobflpmeerxpllzhknmcxgmxiaxhemhzkxleeipiecwtqlopvigbda',null,0,'Dep02','Dep02');
insert into AuthUsers values ('qzwogtzadtzpcundjsnanlwqffzeyrqdcucxtsnogedpmnosrfqanoyikaamsbucfhmqjefffijypdewwrnlpkbnaqlcciklgdmliqyllqcuysicmeqxvcvhlnvfyntrmeuaumxmdldbxkrksmkitisachsrcymnaiytxgysecphnirqlzgttlvuhtkvtqnezfimjqkbl',null,0,'Dep03','Dep03');
insert into AuthUsers values ('rljqzvcnzpiwfnvgafaybthfwcygnxrudukwxtdoezunoithgozkslqbcxyfaegjxadpvrsnsyebjixwiaxvkydumidpjoznvfeumsumizyeoywjftbymwhvazfxpkegvcqwiujwgsyfttqyiejxgxxukaqozonylkbilgurcjyjcpgxjommrsgrpftwrgpowekiepvio',null,0,'Dep04','Dep04');
/*SubDep  15-17 */
insert into AuthUsers values ('fslxiwoueeuuxlkjgaedgtvvqwnatnfqitdcojzgkqwpnofjnnwhtatwfnlurryxhgzzkzjpnrexhxtdrskyaevneoxzmenomrrgaglgudqwuijlrdyhlxntfegxrkjznadeqznpvqgcrigjlemkkiuxlamqjfudfwtxzdzsonqfseyoqozshkzyzxzubllqwfharcoel',null,0,'SubDep01','SubDep01');
insert into AuthUsers values ('ztztmzmovohlcibviudflauhapraotxxypvudxowxhlcnawvestngldswvysfparhkpvxatiegqhirqtxsfexbjhpwyhkxkgfztqtjypzdalkdqwogyqrnwqxqfrhhwozxodfyzjkfsiutqwhtmfyqzaktzsrsxzdatubddecrkjcuptqesgtawztdmvvtzmthqsyqjfx',null,0,'SubDep02','SubDep02');
insert into AuthUsers values ('dqxpsusjiricqjidmmnufgbmdkmmxxorqyucyskgdbmugixverflxfxrevtcgyvqqjfavvkskxizcsfdyfegkwbpmdabygsekpoumjptcyimujrtlczaapjdleuykfztjnqqhubxupjyjruhtbnqjyhljoipwrizvrslqdoktwoeguurnvpxpqhupnwtgjaudrrloopcr',null,0,'SubDep03','SubDep03');

insert into Profile values(1,'User01','User01',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(2,'User02','User02',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(3,'User03','User03',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(4,'User04','User04',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(5,'User05','User05',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(6,'User06','User06',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(7,'User07','User07',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(8,'User08','User08',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(9,'User09','User09',null,2,12345678,null,'<XML>Settings</XML>');
insert into Profile values(10,'User10','User10',null,2,12345678,null,'<XML>Settings</XML>');

insert into Department values(11,'Birken','Dept01',12345678,'Dept@dept.com');
insert into Department values(12,'Egebakken','Dept02',12345678,'Dept@dept.com');
insert into Department values(13,'Talepædagog','Dept03',12345678,'Dept@dept.com');
insert into Department values(14,'Dept04','Dept04',12345678,'Dept@dept.com');

insert into Department values(15,'SubDept01','SubDept01',12345678,'Dept@dept.com');
insert into Department values(16,'SubDept01','SubDept02',12345678,'Dept@dept.com');
insert into Department values(17,'SubDept01','SubDept03',12345678,'Dept@dept.com');


insert into Tags values(null,'Bil');
insert into Tags values(null,'Kat');
insert into Tags values(null,'Abe');
insert into Tags values(null,'Hund');
insert into Tags values(null,'Mand');
insert into Tags values(null,'Sko');
insert into Tags values(null,'Sok');
insert into Tags values(null,'Ål');
insert into Tags values(null,'Taske');
insert into Tags values(null,'Sang');
insert into Tags values(null,'Mor');
insert into Tags values(null,'Far');
insert into Tags values(null,'Søster');
insert into Tags values(null,'Bror');





select * from AuthUsers,Profile,Department
    where AuthUsers.idUser =1;


select * from AuthUsers;

delete from AuthUsers where idUser=1;

select * from Profile;  

select * from HasLink where idMedia = 5 and idSubMedia = 4;

DELETE FROM HasLink WHERE idMedia=5 and idSubMedia=4;
select * from Media
								where idMedia in (select idMedia from MediaProfileAccess
									where idProfile = 1) or ownerID = 1;

select * from Media
    where name like '%d%' or idMedia in (select idMedia from HasTag
                                            where idTag in (select idTag from Tags
                                                            where caption like '%d%'));
SELECT * from Media where mPublic = 1;
select * from HasTag;
select * from Media
    where idMedia in (select idMedia from HasTag
                        where idTag = 3) and (select idMedia from HasTag
                        where idTag = 4);

SELECT * from Media
where idMedia in (select idMedia from MediaProfileAccess where idProfile =4);

select * from MediaProfileAccess;

select * from MediaProfileAccess;
insert into MediaProfileAccess values(3,2);

select * from MediaProfileAccess;
select * from AuthUsers;
select * from Tags;

select * from Profile;
select * from HasDepartment;
select * from ListOfApps;
select * from HasGuardian;
delete from AuthUsers where idUser = 1;

select * from Department 
						where idDepartment not in 
						(select idSubDepartment from HasSubDepartment);
                      
                      
select idTags, caption from Tags 
    where idTags in (select idTag from HasTag 
                    where idMedia = 1);
                    
insert into MediaProfileAccess values (5,11);
select * from Profile
where idProfile in (select idProfile from MediaProfileAccess
                    where idMedia = 1);

select * from Tags; 
select * from Media;
SELECT * from Profile where idProfile = 1;                        
Select * from Profile
						where 
            pRole = 3 and
            idProfile in 
						(select idChild from HasGuardian  
						where idGuardian= 4);

select * from Profile
    where idProfile in
        (select idProfile from HasDepartment
            where idDepartment = 2)
            and pRole = 1;

select * from Department;

select * from HasDepartment;

UPDATE Profile SET pRole = 1 WHERE idProfile = 10 ;

insert into Media values(1,'c:\test','MyTest',1,'public',1);
insert into HasTag values(1,1);
insert into MediaProfileAccess values(5,8);

insert into HasDepartment values (1,6);
insert into HasGuardian values (1,3);
insert into HasGuardian values (4,3);
select * from HasGuardian;

select * from Tags
where caption like '%a';
select * from ListOfApps
where idProfile =1;

select * from Apps
    where idApp in (select idApp from ListOfApps
                    where idProfile = 1);

DELETE FROM HasDepartment WHERE 'idProfile'=1 and 'idDepartment'="+dep.getID()+");
select * from Profile
    where idProfile in 
        (select idGuardian from HasGuardian 
            where idChild=3);

select * from Department
    where idDepartment in
        (select idSubdepartment from HasSubDepartment
            where idDepartment = 2);

select * from Department
    where idDepartment not in (select idSubDepartment from HasSubDepartment);


select * from HasDepartment
    where idDepartment not in (select idSubDepartment from HasSubDepartment);

select * from Department
    where idDepartment in
        (select idDepartment from HasDepartment
                 where  idProfile = '1');

select * from Media where ownerID = 4;

UPDATE Profile SET firstname='Tester'
    where idProfile =1;

select * from HasDepartment;

select * from HasGuardian;

select * from Tags;

SELECT * from AuthUsers;

SELECT * from AuthUsers;

update HasDepartment set idProfile=10, idDepartment=2; 

SELECT idUser, username, idProfile, firstname, middlename, surname from AuthUsers, Profile
    where idUser = idProfile;

select * from HasDepartment;

select * from Department 										
where idDepartment not in (select idSubDepartment from HasSubDepartment);

select * from Department
					where idDepartment in
					(select idSubdepartment from HasSubDepartment
					where idDepartment = 6);


select * from Media 
where idMedia in (select idMedia from MediaProfileAccess
                    where idProfile = 4) or ownerID = 4;
 
                        
insert into HasGuardian values(1,3);

select * from MediaProfileAccess;
select * from Profile;

select password from Profile where idProfile = 7;
select idDepartment, name from Department
    where idDepartment in (select idDepartment from HasDepartment
                    where idProfile =5);

select type from AuthUsers where idUser = 5;

select * from AuthUsers;

select * from Profile
							where idProfile in (select idGuardian from HasGuardian
							where idChild = 5);

UPDATE `04`.`Profile` SET `firstname`='+newFirstname+'  WHERE `idProfile`=1;

select firstname from Profile
where idProfile in (select idGuardian from HasGuardian
    where idChild = 1);

select * from AuthUsers where username = '1';
select * from Profile;
SELECT password from AuthUsers where username = 'Jesper';

select * from HasGuardian;

select * from AuthUsers where username = '10'; 

SELECT firstname, middlename, surname from Profile where idProfile = 1;

select * from Apps
    where 1 in (select idProfile from ListOfApps);
    
select * from Profile
							where pRole = 3;

select idProfile, firstname from Profile
    where 'abekat' in (select certificate from AuthUsers);

SHOW VARIABLES LIKE "%version%"