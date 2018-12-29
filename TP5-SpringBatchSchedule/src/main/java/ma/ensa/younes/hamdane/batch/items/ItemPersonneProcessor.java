package ma.ensa.younes.hamdane.batch.items;

import org.springframework.batch.item.ItemProcessor;

import ma.ensa.younes.hamdane.batch.entities.Personne;


public class ItemPersonneProcessor implements ItemProcessor<Personne, Personne>{

	public Personne process(Personne personne) throws Exception {
		// TODO Auto-generated method stub
		return personne.getCivilite().equals("M") ? personne:null; 
	}

}
