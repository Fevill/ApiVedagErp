package tim.vedagerp.api.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubAccountService {

	@Autowired
	AccountService accountService;

	private static Logger logger = LogManager.getLogger(SubAccountService.class);

		
}
