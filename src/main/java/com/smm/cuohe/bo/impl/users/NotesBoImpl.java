package com.smm.cuohe.bo.impl.users;

import com.smm.cuohe.bo.users.NotesBO;
import com.smm.cuohe.dao.users.NotesDAO;
import com.smm.cuohe.domain.Notes;
import com.smm.cuohe.domain.User;
import com.smm.cuohe.util.DateUtil;
import com.smm.cuohe.util.JSONUtil;
import com.smm.cuohe.util.RestResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author zengshihua
 * 
 *         记录及个人便签
 *
 */
@Service
public class NotesBoImpl implements NotesBO {

	private static Logger logger = Logger.getLogger(NotesBoImpl.class.getName());

	@Resource
	private NotesDAO notesDAO;
	
	@Resource
    private RestTemplate restTemplate;
    
    @Value("#{ch['pricesMonthTrend.URL']}")
	private String pmtURL;

	@Override
	public Notes notesByOrgan(Map<String, Object> paramMap) {

		List<Notes> listNotes = this.notesDAO.notesByOrgan(paramMap);
		if (listNotes.size() > 0) {
			return listNotes.get(0);
		}
		return null;
	}

	/**
	 * 只能修改或新增当天数据
	 * 
	 */
	@Override
	public Integer addnotesByOrgan(User user, Notes notes) {

		Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("itemid", user.getItemId());
		paramMap.put("notedate", DateUtil.doFormatDate(new Date(), "yyyy/MM/dd"));
		paramMap.put("userid", user.getId());

		List<Notes> listNotes = this.notesDAO.notesByOrgan(paramMap);

		Integer count=0;

		if (listNotes.size() == 0) {
			// 新增
			notes.setUpdatedBy(user.getId());
			notes.setUpdatedAt(new Date());

			notes.setItemsId(user.getItemId());
			notes.setCreatedAt(new Date());
			notes.setCreatedBy(user.getId());
			notes.setNoteDate(new Date());
			notes.setNoteType(0);
			count = this.notesDAO.addNotes(notes);

		} else {
			// 修改
			Notes oldNote=listNotes.get(0);

			//检查内容是否相同
			if(oldNote.getContext().equals(notes.getContext())) return -1; //内容相同，无需更新

			oldNote.setContext(notes.getContext());
			oldNote.setUpdatedBy(user.getId());
			count = this.notesDAO.updateNotes(oldNote);

		}
		return count;
	}

	@Override
	public Notes notesByMe(Map<String, Object> paramMap) {

		List<Notes> notesList = this.notesDAO.notesByMe(paramMap);
		if (notesList.size() > 0)
			return notesList.get(0);
		
		return null;
	}

	@Override
	public Integer addnotesByMe(User user, Notes notes) {
		
		Integer count=0;
		
		notes.setUpdatedBy(user.getId());
		notes.setUpdatedAt(new Date());
		
		if(notes.getId()==null){
			// 新增
			notes.setNoteType(1);
			notes.setItemsId(user.getItemId());
			notes.setCreatedAt(new Date());
			notes.setCreatedBy(user.getId());
			notes.setNoteDate(new Date());
			count = this.notesDAO.addNotes(notes);
		}else{
			// 修改
			count = this.notesDAO.updateNotes(notes);
		}
		return count;
	}
	
	/**
	 * 获取指定品类价格6个月走势
	 */
	@Override
	public Map<String, Object> recentlyTrend(User user) {
		
		//根据当前登录的用户，获取所在品目组
		String chartId = user.getItems().getChartId();
		String itemName = user.getItems().getName();
		
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("itemName", itemName);
		
		try {
			String realURL=pmtURL.replace("{0}", chartId);
			
			logger.info("开始调用：获取走势图接口  走势图ID："+chartId);
			
			RestResult pmt=restTemplate.getForObject(realURL,RestResult.class,String.class);
			List<Object[]> seriesVal=new ArrayList<>();
			if(pmt!=null){
				
				if(pmt.getCode()==1){
					
					//数据处理
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> lm=(List<Map<String, Object>>) pmt.getData();
					Object series[]=null;
					
					if(lm!=null&&lm.size()>0){
						//取第一个价格
						NumberFormat nbf=NumberFormat.getInstance();
						nbf.setGroupingUsed(false); //设为false则不使用分组方式显示数据。设为true则使用分组方式显示数据，即每三位数为一个分组，分组间以英文半角逗号分隔。
						/**
						 * 	NumberFormat f = NumberFormat.getInstance();
							f.setGroupingUsed(false);
							System.out.println(f.format(9999999)); //打印出 9999999
							f.setGroupingUsed(true);
							System.out.println(f.format(9999999)); //打印出 9,999,999
						 * 
						 */
						
						double frist=((double) lm.get(0).get("highs")+(double) lm.get(0).get("low"))/2;	
						for(int i=0;i<lm.size();i++){
							series=new Object[2];
							double price=((double) lm.get(i).get("highs")+(double) lm.get(i).get("low"))/2;
							double percent=((price-frist)/frist)*100;
							double date=(Double.valueOf((String)lm.get(i).get("renew_date"))+3600*8)*1000;
							series[0]=nbf.format(date);
							series[1]=percent;
							seriesVal.add(series);
						}
						result.put("minDate",(Double.valueOf((String) lm.get(0).get("renew_date"))+3600*8)*1000);
						result.put("maxDate",(Double.valueOf((String) lm.get(lm.size()-1).get("renew_date"))+3600*8)*1000);
						result.put("seriesVal", JSONUtil.doConvertObject2Json(seriesVal).replaceAll("\"", ""));
						
					}else{
						logger.info("没有数据.....");
					}
					
				}else{
					logger.info(pmt.getErrorMsg());
					result.put("msg", pmt.getErrorMsg());
				}
				
			}else{
				logger.info("接口调用异常......");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取指定品类价格6个月走势调用发生异常");
		}
		return result;
	}

	@Override
	public List<String> getNotesCreateDateByUser(int user_id,int item_id) {

		List<String> dates=this.notesDAO.getNotesCreateDateByUser(user_id,item_id);

		if(dates==null || dates.size()==0) return null;

		List<String> data=new ArrayList<String>();

		for (String date:dates){

			SimpleDateFormat format=new SimpleDateFormat("yyyy/M/d");

			//日期格式转换
			String formate_date= null;
			try {
				formate_date = format.format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			data.add(formate_date);

		}

		dates=null;

		return data;
	}
}
