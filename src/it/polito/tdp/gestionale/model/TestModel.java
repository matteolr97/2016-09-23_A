package it.polito.tdp.gestionale.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo();
		for(StudenteCorso s:model.getFrequency())
		System.out.println(s);
		model.contaCorsi();
		for(Corso c:model.getMinimoCorsi())
			System.out.println(c);
	}

}
