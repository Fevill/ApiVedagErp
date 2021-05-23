package tim.vedagerp.api.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

public interface Ibalance {

	String getNumber();

    String getLabel();
    
	float getCredit();

	float getDebit();
}
