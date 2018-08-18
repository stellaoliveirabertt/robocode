package stellaoliveiram;

import robocode.Robot;
import java.awt.*;
import robocode.*;

//Suporte Robocode - http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

public class StellaOliveiraM extends Robot
{
	//Variaveis para uso
	double quantMovimento; 
	double anguloAdv = 0;

	// Movimentação do Robo (Jogada - Movimentar pelas paredes)
	public void run() {
		// Definindo as cores do robô
		setBodyColor	(Color.red); 	 //Corpo
		setGunColor		(Color.black);   //Canhão
		setRadarColor	(Color.black);   //Radar

		// Passa a quantidade de movimento para o máximo possível do campo
		quantMovimento = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		ahead(quantMovimento); //Busca o canto e avança
		// Direciona o canhão a 90º e vira o veículo 90º a esquerda
		turnGunRight(90);
		turnLeft(90);
	
		while (true) {
			ahead(quantMovimento); //Avança de acordo com o parametro da arena
			turnRight(90); //Gira o veículo 90º a direita
		}
	}

	//Colisão com outro veículo
	public void onHitRobot(HitRobotEvent e) {
		// Caso esteja na frente recue e vire 90º a direita
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(150);
			turnRight(90);
		}
		else { //Caso esteja atrás recue e vire 90º a esquerda
			ahead(100);
			turnLeft(90);
		}
	}

	//Sempre que encontrado um robo atire
	public void onScannedRobot(ScannedRobotEvent e) {
		// Se o outro robô está próximo, e ele tem pouca energia e eu muita energia, dispara intensamente!
			if (e.getDistance() < 50 && e.getEnergy() < 50 && getEnergy() > 50) {
				fire(3);
			} //Caso contrário, atira com intensidade 1.
			else {
				fire(1);
			}	
		back(e.getDistance() - 10); //Calcula a distância pro inimigo recua
		scan(); // usa o radar novamente para localizar os oponentesic 
	}
}
