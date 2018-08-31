package org.craftedsw.tripservicekata.user;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.craftedsw.tripservicekata.trip.UserBuilder;
import org.junit.Test;

public class UserTest {
	
	private static final User BOB = new User();
	private static final User PAUL = new User();
	
	@Test public void
	should_inform_when_users_are_not_friends() {
		User user = UserBuilder.aUser()
						.friendsWith(BOB)
						.build();
		assertThat(user.isFriendsWith(PAUL), is(false));
	}
	
	@Test public void 
	should_inform_when_users_aer_friends() {
		User user = UserBuilder.aUser()
						.friendsWith(BOB, PAUL)
						.build();
		
		assertThat(user.isFriendsWith(PAUL), is(true));
		
	}
}
