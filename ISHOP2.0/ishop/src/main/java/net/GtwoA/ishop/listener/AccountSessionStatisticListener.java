package net.GtwoA.ishop.listener;



import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.GtwoA.ishop.constant.Constants;



@WebListener
@SuppressWarnings("unchecked")
public class AccountSessionStatisticListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		List<String> actions = (List<String>) se.getSession().getAttribute(Constants.ACCOUNT_ACTIONS_HISTORY);
		if(actions!=null) {
			logCurrentActionHistory(se.getSession().getId() , actions);
		}
		// TODO Auto-generated method stub
		
	}
	private void logCurrentActionHistory(String sessionId, List<String> actions) {
		System.out.println(sessionId + " ->\n\t" +String.join("\n\t", actions));
		
	}

}
