var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/pospathways";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var myobj = [
    {name:'Roslyn Bailey-Oddman',phone:'954-201-8295 ',email:'rbailey2@broward.edu ',institution:'Broward College – Davie',position:'Manager of Student Affairs',brief:'',picurl:'', type:'Member'},
{name:'Henri Benlolo',phone:'352-854-2322 ext. 1430 ',email:'benloloh@cf.edu ',institution:'College of Central Florida – Ocala',position:'Dean of Student Services',brief:'',picurl:'', type:'Member'},
{name:'Brooke Mathis',phone:'850-718-2437 ',email:'mathisb@chipola.edu ',institution:'Chipola College – Marianna',position:'Student Advisor, Student Athletes & International Students',brief:'',picurl:'', type:'Member'},
{name:'Beth Hoodiman',phone:'386-506-3619 ',email:'hoodimb@daytonastate.edu ',institution:'Daytona State College – Daytona Beach',position:'Associate Director of Admissions',brief:'',picurl:'', type:'Member'},
{name:'Dr. Sandra Handfield',phone:'321-433-5091 ',email:'handfields@easternflorida.edu',institution:'Eastern Florida State College – Melbourne',position:'Provost, Melbourne Campus',brief:'',picurl:'', type:'Member'},
{name:'Donna Lee',phone:'386-754-4208 ',email:'DonnaLee@fgc.edu ',institution:'Florida Gateway College – Lake City',position:'',brief:'',picurl:'', type:'Member'},
{name:'Louis D. Lindsey',phone:'904-632-3304 ',email:'L.Lindsey@fscj.edu ',institution:'Florida SouthWestern State College – Fort Myers',position:'Student Success Advisor',brief:'',picurl:'', type:'Member'},
{name:'Denise Giarrusso',phone:'904-997-2527 ',email:'denise.giarrusso@fscj.edu',institution:'Florida State College at Jacksonville – Jacksonville  ',position:'Associate Director of Student Success',brief:'',picurl:'', type:'Member'},
{name:'John Rouge',phone:'305-809-3181 ',email:'john.rouge@fkcc.edu ',institution:'Florida Keys Community College – Key West',position:'Assistant Director of Student Success, Disability Services',brief:'',picurl:'', type:'Member'},
{name:'Melissa Lavender',phone:'850-747-3211 ',email:'mlavender@gulfcoast.edu ',institution:'Gulf Coast State College – Panama City  ',position:'Vice President, Student Affairs',brief:'',picurl:'', type:'Member'},
{name:'Warren Smith',phone:'813-253-7984 ',email:'wsmith3@hccfl.edu',institution:'Hillsborough Community College – Tampa',position:'Pre-Collegiate Liaison Officer, Student Services and Enrollment Development',brief:'',picurl:'', type:'Member'},
{name:'Joseph Lemieux',phone:'772-462-7129 ',email:'jlemieux@irsc.edu ',institution:'Indian River State College – Fort Pierce',position:'Advisor Specialist',brief:'',picurl:'', type:'Member'},
{name:'Carolyn Scott',phone:'352-536-2211 ',email:'scottc@lssc.edu ',institution:'Lake-Sumter State College – Leesburg',position:'Director of Student Development',brief:'',picurl:'', type:'Member'},
{name:'Barbara Pryor Dumornay',phone:'305-237-1448 ',email:'bpryor@mdc.edu ',institution:'Miami Dade College – Miami',position:'Director of Retention and Transition Services',brief:'',picurl:'', type:'Member'},
{name:'Suzie Cashwell',phone:'850-973-9432 ',email:'cashwells@nfcc.edu ',institution:'North Florida Community College – Madison  ',position:'Director of Student Support Services',brief:'',picurl:'', type:'Member'},
{name:'Dr. Aimee Watts ',phone:'850-729-5237 ',email:'wattsa@nwfsc.edu',institution:'Northwest Florida State College – Niceville',position:'Dean of Students',brief:'',picurl:'', type:'Member'},
{name:'Latisha Myrick',phone:'561-868-3819 ',email:'myrickl@palmbeachstate.edu ',institution:'Palm Beach State College – Lake Worth',position:'',brief:'',picurl:'', type:'Member'},
{name:'Bob Bade',phone:'727-816-3413 ',email:'badeb@phsc.edu ',institution:'Pasco-Hernando State College – New Port Richey',position:'Vice President, Student Affairs and Enrollment Management',brief:'',picurl:'', type:'Member'},
{name:'Kim Calloway',phone:'850-484-1858 ',email:'kcalloway@pensacolastate.edu ',institution:'Pensacola State College – Pensacola',position:'Counselor, TRiO Support Services',brief:'',picurl:'', type:'Member'},
{name:'Kathy Bucklew',phone:'863-298-6820 ',email:'kbucklew@polk.edu ',institution:'Polk State College – Winter Haven',position:'Director, Student Enrollment/Registrar',brief:'',picurl:'', type:'Member'},
{name:'Tanya Fritz',phone:'407-708-2897 ',email:'fritzt@seminolestate.edu ',institution:'Seminole State College of Florida – Sanford',position:'Coordinator & Advisor, First Generation Freshmen',brief:'',picurl:'', type:'Member'},
{name:'Summer Miller',phone:'863-784-7447 ',email:'|millers@southflorida.edu ',institution:'South Florida State College – Avon Park  ',position:'College Recruiter',brief:'',picurl:'', type:'Member'},
{name:'Gary Russell',phone:'941-752-5200 ',email:'russelg@scf.edu ',institution:'State College of Florida, Manatee-Sarasota – Bradenton  ',position:'Vice President, Academic Affairs',brief:'',picurl:'', type:'Member'},
{name:'Gilbert Evans',phone:'386-312-4200 ',email:'gilbertevans@sjrstate.edu',institution:'St. Johns River State College – Palatka  ',position:'Vice President, Student Affairs/Assistant General Counsel',brief:'',picurl:'', type:'Member'},
{name:'Cheryl Kerr',phone:'727-341-3736 ',email:'Kerr.Cheryl@spcollege.edu ',institution:'St. Petersburg College – St. Petersburg',position:'Program Director, II',brief:'',picurl:'', type:'Member'},
{name:'Emily Rattini-Reich',phone:'850-201-9767 ',email:'rattinie@tcc.fl.edu ',institution:'Tallahassee Community College – Tallahassee  ',position:'Fostering Achievement Fellowship Program',brief:'',picurl:'', type:'Member'},
{name:'Shanay Morris',phone:'407-582-5620 ',email:'smorris44@valenciacollege.edu',institution:'Valencia College – Orlando',position:'Program Advisor',brief:'',picurl:'', type:'Member'},
{name:'Wendy Joseph',phone:'305-237-1514 ',email:'wjoseph@mdc.edu',institution:'Miami Dade College – Miami',position:'Program Manager, Educate Tomorrow',brief:'',picurl:'', type:'Member'},
{name:'Audra Liswith',phone:'727-341-3032 ',email:'liswith.audra@spcollege.edu',institution:'St. Petersburg College – St. Petersburg',position:'College Testing Placement Coordinator',brief:'',picurl:'', type:'Member'},
{name:'Charmaine Lowe',phone:'407-582-5046 ',email:'clowe16@valenciacollege.edu',institution:'Valencia College – Orlando',position:'Success Coach',brief:'',picurl:'', type:'Member'},
{name:'Dr. Jovany Felix',phone:'850-599-3180 ',email:'Jovany.felix@famu.edu',institution:'Florida A&M University – Tallahassee',position:'Director, Center for Disability Access and Resources (CEDAR)',brief:'',picurl:'', type:'Member'},
{name:'Jasmine Briggs',phone:'561-297-6845 ',email:'briggsj@fau.edu',institution:'Florida Atlantic University – Boca Raton',position:'ACCESS Academic Coach/Advisor',brief:'',picurl:'', type:'Member'},
{name:'Lauren Strunk',phone:'239-590-1866 ',email:'lstrunk@fgcu.edu',institution:'Florida Gulf Coast University – Fort Myers',position:'Case Manager, Student Affairs',brief:'',picurl:'', type:'Member'},
{name:'Ana I Ramos, MSW',phone:'305-348-6106 ',email:'anramos@fiu.edu',institution:'Florida International University – Miami ',position:'Success Coach, Fostering Panther Pride',brief:'',picurl:'', type:'Member'},
{name:'Lisa Pessin, MSW',phone:'850-644-9699 ',email:'lpessin@fsu.edu',institution:'Florida State University – Tallahassee   ',position:'Coordinator, Unconquered Scholars Program',brief:'',picurl:'', type:'Member'},
{name:'Jessica Maxon',phone:'941-487-4548 ',email:'jmaxon@ncf.edu',institution:'New College of Florida – Sarasota',position:'Director of First Year Programs',brief:'',picurl:'', type:'Member'},
{name:'Leslie Pendleton',phone:'352-392-1265 ',email:'lesliep@ufsa.ufl.edu',institution:'University of Central Florida – Orlando',position:'Director, Florida Opportunity Scholars Program',brief:'',picurl:'', type:'Member'},
{name:'Tom VanSchoor',phone:'904-620-1577 ',email:'tvanscho@unf.edu',institution:'University of North Florida - Jacksonville  ',position:'Dean of Students',brief:'',picurl:'', type:'Member'},
{name:'Makenzie Schiemann',phone:'813-974-6130 ',email:'mschiemann@usf.edu ',institution:'University of South Florida – Tampa ',position:'Director, Office of Student Outreach and Support, SOCAT-Students of Concern Assistance Team',brief:'',picurl:'', type:'Member'},
{name:'Dr. Lusharon Wiley',phone:'850-474-2384 ',email:'lwiley@uwf.edu',institution:'University of West Florida – Pensacola',position:'Senior Associate Dean of Students & Director of Student Case Management Services',brief:'',picurl:'', type:'Member'},
{name:'Lynda A. Page',phone:'850-245-0466 ',email:'Lynda.Page@flbog.edu',institution:'State University System of Florida Board of Governors – Tallahassee',position:'Director of Articulation',brief:'',picurl:'', type:'Member'},
{name:'Dr. Demetriss Locke',phone:'850-412-5916 ',email:'demetriss.locke@famu.edu',institution:'Florida A&M University – Tallahassee',position:'Assistant Director, Office of University Retention',brief:'',picurl:'', type:'Member'},
{name:'Jess E. Tuck, Ph.D.',phone:'561-297-2072 ',email:'jtuck@fau.edu',institution:'Florida Atlantic University – Boca Raton',position:'Director, ACCESS Program & Title III Project Director',brief:'',picurl:'', type:'Member'},
{name:'Todd Wells',phone:'813-974-7595 ',email:'toddwells@usf.edu',institution:'University of South Florida – Tampa',position:'Interim Director, Center for Leadership & Civic Engagement',brief:'',picurl:'', type:'Member'}
  ];
  db.collection("profiles").insertMany(myobj, function(err, res) {
    if (err) throw err;
    console.log("Number of documents inserted: " + res.insertedCount);
    db.close();
  });
});