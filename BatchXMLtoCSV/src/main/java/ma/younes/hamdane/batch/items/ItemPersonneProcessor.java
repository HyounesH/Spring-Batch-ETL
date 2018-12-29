package ma.younes.hamdane.batch.items;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import ma.younes.hamdane.batch.entities.Personne;

public class ItemPersonneProcessor implements ItemProcessor<Personne, Personne> {
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");
	public Personne process(Personne personne) throws Exception {
		
		int YearOB=Integer.parseInt(dateFormat.format(personne.getDob()));
		int currentYear=Integer.parseInt(dateFormat.format(new Date()));
		
		return (currentYear-YearOB)<30? personne:null;
	}

}
