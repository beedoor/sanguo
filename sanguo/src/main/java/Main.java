import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.sanguo.domain.UserBean;
import com.game.sanguo.task.CitySearchAndGoldTask;
import com.game.sanguo.task.GetWordCityInfoTask;
import com.game.sanguo.task.LoginTask;


public class Main {

	final static ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
	protected static Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserBean userBean = new UserBean("564195410@qq.com", "h156788");
		//登录
		exec.submit(new LoginTask(userBean));
		//扫描全图信息
//		exec.submit(new GetTimeZoneTask(userBean));
		//获取资源点信息
//		exec.submit(new GetWordCityInfoTask(userBean));
//		exec.scheduleAtFixedRate(new CitySearchAndGoldTask(userBean), 0, 10, TimeUnit.MINUTES);
		logger.info("sessionId "+userBean.getSessionId()+" chatSessionId " +userBean.getChatSessionId()+" checkId="+userBean.getCheckId()+" " + userBean.getBatchId()+"\t"+userBean.getNumberIdNoIncrement());
	}
}
