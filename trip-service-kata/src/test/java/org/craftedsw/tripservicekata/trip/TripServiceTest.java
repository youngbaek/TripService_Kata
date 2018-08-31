package org.craftedsw.tripservicekata.trip;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
import static org.craftedsw.tripservicekata.trip.UserBuilder.*;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.trip.TripService;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;


public class TripServiceTest {
	
	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();
	private User loggedInUser;
	
	private TripService tripService;
	
	@Before
	public void initialise(){
		tripService = new TestableTripService();
		loggedInUser = REGISTERED_USER;
	}
	
	@Test(expected = UserNotLoggedInException.class) public void
	should_throw_on_exception_when_user_is_not_logged_in() {
		loggedInUser = GUEST;
		tripService.getTripsByUser(UNUSED_USER);
	}
	
	@Test public void
	should_not_return_any_trips_when_users_are_not_friends(){		
		User friend = aUser()
							.friendsWith(ANOTHER_USER)
							.withTrips(TO_BRAZIL)
							.build();
		
		List<Trip> friendTrips = tripService.getTripsByUser(friend);
		
		assertThat(friendTrips.size(), is(0));;
	}
	
	@Test public void
	should_return_friend_trips_when_users_are_friends(){
		User friend = aUser()
							.friendsWith(ANOTHER_USER, loggedInUser)
							.withTrips(TO_BRAZIL, TO_LONDON)
							.build();
		
		List<Trip> friendTrips = tripService.getTripsByUser(friend);
		
		assertThat(friendTrips.size(), is(2));;
	}
	
	private class TestableTripService extends TripService {

		@Override 
		protected User getLoggedInUser() {
			return loggedInUser;
		}

		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();
		}
		
	}
	
}
