package agents;

import jdk.nashorn.internal.ir.RuntimeNode;

public class Monkey extends Agent
{
        private tableMonkey privateTable;

	private static int totalOfInstances = 0;
        private static double learning=0.0005;
    public static double getLearning() {
        return learning;
    }

    public static void setLearning(double aLearning) {
        learning = aLearning;
    }
	
        
        
        
        public static void setTotalOfInstances(int totalOfInstances){
            Monkey.totalOfInstances=totalOfInstances;
        }
        
        public Monkey()
	{
		super();
		// Incrementa o total de instâncias da classe para nomear os objetos criados
		totalOfInstances++;

		// Nomea o macaco como M1, por exemplo
		setName("M" + totalOfInstances);

		// Define a tabela de acordo com a quantidade de símbolos e predadores especificados no programa
		setTable(new tableMonkey());

		// Cria uma variável do tipo Random para definir números aleatórios

		// Popula a tabela criada com valores flutuantes aleatórios entre 0.0 e 0.9
                
                this.makeTable();
		
	}
        
        private void makeTable(){
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < getTable().table.length; i++)
		{
			for (int j = 0; j < getTable().table[0].length; j++)
			{
				this.getTable().table[i][j] = random.nextDouble()*90/100;
			}
		}
        }
        
	public final tableMonkey getTable()
	{
		return privateTable;
	}
	public final void setTable(tableMonkey value)
	{
		privateTable = value;
	}
	


	public Predator CheckArea()
        {
            int iRecalc;
            int jRecalc;

            for (int i = Florest.getMapSizeX() - Florest.getVisionRadius(); i < Florest.getMapSizeX() + Florest.getVisionRadius(); i++)
            {
                if (i >= Florest.getMapSizeX())
                {
                    iRecalc = i - Florest.getMapSizeX();
                }
                else if (i < 0)
                {
                    iRecalc = i + Florest.getMapSizeX();
                }
                else
                {
                    iRecalc = i;
                }

                for(int j = Florest.getMapSizeY() - Florest.getVisionRadius(); j < Florest.getMapSizeY()+ Florest.getVisionRadius(); j++)
                {
                    if (j >= Florest.getMapSizeY())
                    {
                        jRecalc = j - Florest.getMapSizeY();
                    }
                    else if (j < 0)
                    {
                        jRecalc = j + Florest.getMapSizeY();
                    }
                    else
                    {
                        jRecalc = j;
                    }

                    if (Florest.getMap()[iRecalc][jRecalc] != null && Florest.getMap()[iRecalc][jRecalc] != this)
                    {
                        if (Florest.getMap()[iRecalc][jRecalc].getClass() == Predator.class)
                        {
                            return (Predator)Florest.getMap()[iRecalc][jRecalc];
                        }
                    }
                }
            }

            return null;
        }

        public void SendSignal(Predator predator)
        {
            double highest = 0.0;
            int indexSymbol = 0;
            int indexPredator = 0;

            for (int i = 0; i < Florest.getPredatorsAmount(); i++)
            {
                if (Florest.getPredators()[i] == predator)
                    indexPredator = i;
            }

            // Obtém o sinal de maior valor para o predador encontrado
            for (int i = 0; i < Florest.getSymbols(); i++)
            {
                if (this.getTable().table[i][indexPredator] > highest)
                {
                    highest = this.getTable().table[i][indexPredator];
                    indexSymbol = i;
                }
            }

            int iRecalc;
            int jRecalc;

            // Envia o sinal para o raio em X definido
            for (int i = super.getPlace().getX() - Florest.getSignalRadius(); i <= super.getPlace().getX() + Florest.getSignalRadius(); i++)
            {
                // O Mapa é esférico.
                if (i >= Florest.getMapSizeX())
                {
                    iRecalc = i - Florest.getMapSizeX();
                }
                else if (i < 0)
                {
                    iRecalc = i + Florest.getMapSizeX();
                }
                else
                {
                    iRecalc = i;
                }

                // Envia o sinal para o raio em Y definido
                 for (int j = super.getPlace().getY() - Florest.getSignalRadius(); j <= super.getPlace().getY() + Florest.getSignalRadius(); j++)
                    
                {
                    // O Mapa é esférico
                    if (j >= Florest.getMapSizeY())
                    {
                        jRecalc = j - Florest.getMapSizeY();
                    }
                    else if (j < 0)
                    {
                        jRecalc = j + Florest.getMapSizeY();
                    }
                    else
                    {
                        jRecalc = j;
                    }

                    // Verifica se encontrou um macaco
                    if (Florest.getMap()[iRecalc][jRecalc] != null && Florest.getMap()[iRecalc][jRecalc].getClass() == Monkey.class)
                    {
                        if(Florest.getMap()[iRecalc][jRecalc] != this)
                        {
                            Monkey monkey = (Monkey)Florest.getMap()[iRecalc][jRecalc];

                            //Console.WriteLine(monkey.Label + " recebeu o sinal de " + this.Label);

                            Predator predatorSeen = monkey.CheckArea();

                            // Atualiza a tabela para o predador visto pelo macaco que recebeu o sinal
                            if (predatorSeen != null)
                            {
                              
                                double newValue = this.increaseProbabilitySymbol(monkey.getTable().table[indexSymbol][indexPredator]);
                                
                                if (newValue <= 1)
                                {

                                    monkey.getTable().table[indexSymbol][indexPredator] = newValue;
                                }
                                else
                                {
                                    monkey.getTable().table[indexSymbol][indexPredator] = 1;
                                }
                            }
                        }
                    }
                }
            }
        }

        private double increaseProbabilitySymbol(double probability){
           
            return probability+Monkey.learning;
        }
        
	public final void ShowTable()
	{

		System.out.print("\n\tMonkey " + this.getName() + "\n\n\t");

		for (Predator predator : Florest.getPredators())
		{
			System.out.print("\t" + predator.getName());
		}

		System.out.println();

               
        
		for (int i = 0; i < getTable().table.length; i++)
		{
			System.out.print("\tS" + (i + 1) + "\t");
			for (int j = 0; j < getTable().table[0].length; j++)
			{
                                
				System.out.printf("%.2f\t",getTable().table[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void Move()
	{
		super.Move();
		Predator predator = CheckArea();
		if (predator != null)
		{
			//Console.WriteLine(this.Label + " viu o predador " + predator.Label);
			SendSignal(predator);
		}
	}
}