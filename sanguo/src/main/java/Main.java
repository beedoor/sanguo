import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.sanguo.domain.UserBean;
import com.game.sanguo.task.CitySearchAndGoldTask;
import com.game.sanguo.task.ContinuousLoginDaysRewardTask;
import com.game.sanguo.task.GameHelper;
import com.game.sanguo.task.GameNotifyTask;
import com.game.sanguo.task.GetWordCityInfoTask;
import com.game.sanguo.task.LoginTask;
import com.game.sanguo.task.MsgItemSellTask;
import com.game.sanguo.util.ItemConfig;
import com.game.sanguo.util.ResourceConfig;
import com.game.sanguo.util.UserConfig;

public class Main {

	protected static Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserConfig userConfig = new UserConfig();
		ResourceConfig resourceConfig = new ResourceConfig();
		ItemConfig itemConfig = new ItemConfig();

		userConfig.loadUserConfig();
		resourceConfig.loadResourceConfig();
		
		itemConfig.loadUserConfig();

		UserBean userBean = userConfig.getUserConfig(null);
		// 加载资源配置
		userBean.setItemConfig(itemConfig);

		// 登录
		GameHelper.submit(new LoginTask(userBean));
		// 维持会话的获取通知信息惹任务
		GameHelper.scheduleAtFixedRate(new GameNotifyTask(userBean), 10, TimeUnit.SECONDS);
		// 扫描全图信息
		// exec.submit(new GetTimeZoneTask(userBean));
		// 领取每日登录奖励
		GameHelper.scheduleAtFixedRate(new ContinuousLoginDaysRewardTask(userBean), 24, TimeUnit.HOURS);

		// 扫描宝山，黑市，兵营，金矿资源定时任务
		GameHelper.submit(new GetWordCityInfoTask(userBean, resourceConfig));
		// 卖东西定时任务
		GameHelper.scheduleAtFixedRate(new MsgItemSellTask(userBean, itemConfig), 24, TimeUnit.HOURS);
		// 领取资源，宝箱等定时搜索任务
		GameHelper.scheduleAtFixedRate(new CitySearchAndGoldTask(userBean), 10, TimeUnit.MINUTES);
	}
}
