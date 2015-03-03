package larson.pm;

import java.util.List;

import larson.pm.adapter.FondLVAdapter;
import larson.pm.bean.Fond;
import larson.pm.dao.FondDao;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class FondBillActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fond_bill);
		
		init();
	}
	
	public void init(){
		ListView fond_bill_lv = (ListView) findViewById(R.id.fond_bill_lv);
		
		FondDao dao  = new FondDao(this);
		List<Fond> fonds = dao.queryAll();
		FondLVAdapter adapter = new FondLVAdapter(this, fonds);
		fond_bill_lv.setAdapter(adapter);
		
	}
	
}
