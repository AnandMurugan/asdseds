package Profiler.profile;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

//import recommender.Cluster;
//import recommender.ClusterArray;
//import recommender.RecommendationItemUI;
//
//import com.myprofile.profile.MuseumItem;
//import com.myprofile.profile.ObjectFactory;
//import com.myprofile.profile.ProfileType;


public class ProfileManager {

	public ProfileType getProfile(String profilePath, int profileNr) {
		ProfileType profile = loadProfile(profilePath);
		if(profile == null) {
			if(profileNr == 1) {
				profile = createProfile1();
			} else {
				profile = createProfile2();
			}
		}
		
		return profile;
	}
	
	private ProfileType createProfile1() {

		ObjectFactory factory=new ObjectFactory();
		ProfileType profile = factory.createProfileType();
		/**
		 * The following sample code shows how you can set the profile content, using the provided java Code
		 */
		/*--------------------------------------------*/
		//initialize basic information
		profile.setAge(33);
		/** only one of : "higher education","postsecondary education","elementary education","secondary education" */
		profile.setEducation("higher education");
		/** only one of: "all",	"adventure","art of culture", "educational", "welfare and relaxing" */
		profile.setMotivationOfVisit("educational");
		/** only use of these values:{ greedy, normal, conservative} */
		profile.setAttitude("greedy");
		profile.setPrivacy(0.5);
		/*--------------------------------------------*/
		//add interests into profile 
		ProfileType.Interests interests = factory.createProfileTypeInterests();

		/** choose any value of Subject, Object Type, Material from provided list */
		interests.getInterest().add("astronomy");
		interests.getInterest().add("art");
		// add collection of interests into profile
		profile.setInterests(interests);
		profile.setVisitedItems(factory.createProfileTypeVisitedItems());
		/*--------------------------------------------*/

		return profile;
	}
	
	private ProfileType createProfile2() {

		ObjectFactory factory=new ObjectFactory();
		ProfileType profile = factory.createProfileType();
		/**
		 * The following sample code shows how you can set the profile content, using the provided java Code
		 */
		/*--------------------------------------------*/
		//initialize basic information
		profile.setAge(33);
		/** only one of : "higher education","postsecondary education","elementary education","secondary education" */
		profile.setEducation("higher education");
		/** only one of: "all",	"adventure","art of culture", "educational", "welfare and relaxing" */
		profile.setMotivationOfVisit("educational");
		/** only use of these values:{ greedy, normal, conservative} */
		profile.setAttitude("greedy");
		profile.setPrivacy(0.5);
		/*--------------------------------------------*/
		//add interests into profile 
		ProfileType.Interests interests = factory.createProfileTypeInterests();

		/** choose any value of Subject, Object Type, Material from provided list */
		interests.getInterest().add("astronomy");
		interests.getInterest().add("art");
		// add collection of interests into profile
		profile.setInterests(interests);
		/*--------------------------------------------*/

		return profile;
	}

	public void dumpProfile (ProfileType profile, String outputFilename) {
		JAXBContext jc;
		Marshaller marshaller;

		try {
			//dump profile into an XML file
			jc = JAXBContext.newInstance("Profiler.profile");
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			String outputFileName = outputFilename;
			marshaller.marshal( profile , new FileOutputStream(outputFileName));
		} catch (JAXBException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private ProfileType loadProfile(String inputFileName ) {
		JAXBContext jc;
		Unmarshaller u;

		/* Read data */
		try {
			jc = JAXBContext.newInstance("Profiler.profile");
			u = jc.createUnmarshaller();
			JAXBElement<ProfileType> root = u.unmarshal(new StreamSource(new File(inputFileName)),ProfileType.class);
			ProfileType profile = root.getValue();

			return profile;
		} catch (JAXBException e) {
			//e.printStackTrace();
			return null;
		}
	}


	//	public void dumpProfileStep2(ProfileType profileStep1, ClusterArray recommendation_results, String profile2Filename) {
	//		ObjectFactory factory=new ObjectFactory();
	//		ProfileType profileStep2 = factory.createProfileType();
	//		// copy basic info + interests form step1 profile to the new profile
	//		profileStep2.setAge(profileStep1.getAge());
	//		profileStep2.setAttitude(profileStep1.getAttitude());
	//		profileStep2.setEducation(profileStep1.getEducation());
	//		profileStep2.setMotivationOfVisit(profileStep1.getMotivationOfVisit());
	//		profileStep2.setPrivacy(profileStep1.getPrivacy());
	//		profileStep2.setInterests(profileStep1.getInterests());
	//		//--------------------------
	//		Cluster[] result_clusters = recommendation_results.getItem();
	//		
	//		ProfileType.VisitedItems visitedItems =  factory.createProfileTypeVisitedItems();
	//		for (int i = 0; i < result_clusters.length; i++) {
	//			RecommendationItemUI[] recommendationItems = result_clusters[i].getRecommendationItems();
	//			
	//			for (int j = 0; j < recommendationItems.length; j++) {
	//			    MuseumItem vitem = factory.createMuseumItem();
	//			    vitem.setId(recommendationItems[j].getUri());
	//			    vitem.setName(recommendationItems[j].getName());
	//			    vitem.setRating(recommendationItems[j].getScore());
	//			    visitedItems.getVisitedItem().add(vitem);
	//			}
	//		}
	//		profileStep2.setVisitedItems(visitedItems);
	//		
	//		dumpProfile (profileStep2, profile2Filename);
	//	}
	//	
	//	
	//	public static void main (String[]  args) {
	//		ProfileManager profiler = new ProfileManager();
	//		profiler.createProfile();
	//		profiler.loadProfile("Profile.xml");
	//	}
}
