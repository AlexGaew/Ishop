package net.GtwoA.ishop.listener;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import net.GtwoA.ishop.constant.Constants;



@WebListener
@SuppressWarnings("unchecked")
public class AcountRequestStatisticListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
		List<String> actions = (List<String>) request.getSession().getAttribute(Constants.ACCOUNT_ACTIONS_HISTORY);
		if (actions == null) {
			actions = new ArrayList<String>();
			request.getSession().setAttribute(Constants.ACCOUNT_ACTIONS_HISTORY, actions);
		}
		actions.add(getCurrentAction(request));

		// TODO Auto-generated method stub

	}
	private String getCurrentAction(HttpServletRequest req) {
		StringBuilder sBuilder = new StringBuilder(req.getMethod()).append(" ").append(req.getRequestURI());
		Map<String, String[]> map = req.getParameterMap();
		if(map!=null) {
			boolean first = true;
			for(Map.Entry<String,String[]> entry: map.entrySet()){
				if(first) {
					sBuilder.append('?');
					first=false;
				} else {
					sBuilder.append('&');
				}
				for (String value : entry.getValue()) {
					sBuilder.append(entry.getKey()).append('=').append(value).append('&');
				}
				sBuilder.deleteCharAt(sBuilder.length()-1);
				
			}
					
			
		}
		return sBuilder.toString();
			
		
	}
}
