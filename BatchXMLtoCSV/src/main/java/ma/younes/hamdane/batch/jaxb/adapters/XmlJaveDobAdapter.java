package ma.younes.hamdane.batch.jaxb.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlJaveDobAdapter extends XmlAdapter<String, Date> {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

	@Override
	public String marshal(Date date) throws Exception {

		return dateFormat.format(date);
	}

	@Override
	public Date unmarshal(String date) throws Exception {
		// TODO Auto-generated method stub
		return dateFormat.parse(date);
	}

}
