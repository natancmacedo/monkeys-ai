package agents;

public class Predator extends Agent
{
	public static int predatorCounter;

        public Predator()
	{
		super();
		super.setIndex(predatorCounter);

		predatorCounter++;
		super.setName("P" + predatorCounter);
	}
        
    public static int getPredatorCounter() {
        return predatorCounter;
    }      

}