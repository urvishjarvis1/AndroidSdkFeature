package com.example.volansys.roomdatabase;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.volansys.roomdatabase.model.User;
import com.example.volansys.roomdatabase.model.UserDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleInstrumentedTest {
    UserDao userDao=Mockito.mock(UserDao.class);
    User user=Mockito.mock(User.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.volansys.roomdatabase", appContext.getPackageName());

    }
    @Test
    public void dataBaseTest(){
        Mockito.when(userDao.getUserById(Mockito.anyString())).thenReturn(new User("1", "urvish", Converter.toDate(System.currentTimeMillis())));
        assertEquals(userDao.getUserById("1").getUserName(),"urvish");
    }
}
