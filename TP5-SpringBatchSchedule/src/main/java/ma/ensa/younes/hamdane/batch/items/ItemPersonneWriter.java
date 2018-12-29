package ma.ensa.younes.hamdane.batch.items;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import ma.ensa.younes.hamdane.batch.dao.Doa;
import ma.ensa.younes.hamdane.batch.entities.Personne;

public class ItemPersonneWriter implements ItemWriter<Personne> {

	@Autowired
	private Doa dao;

	public void write(List<? extends Personne> personnes) throws Exception {
		for (Personne personne : personnes) {
			dao.addPersonne(personne);
		}

	}

}
