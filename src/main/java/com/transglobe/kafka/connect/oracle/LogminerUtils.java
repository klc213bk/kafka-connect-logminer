package com.transglobe.kafka.connect.oracle;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.transglobe.streamingetl.common.util.StreamingEtlUtils;

public class LogminerUtils {
    static final Logger log = LoggerFactory.getLogger(LogminerUtils.class);

	public static void updateStreamingEtlLogminerState(Connection conn, Long logminerState) {
		try {
			StreamingEtlUtils.updateStreamingEtlLogminerState(conn, logminerState);
		} catch (Exception e1) {
			log.error(">>> error call updateStreamingEtlLogminerState state={}, exception={}", logminerState, e1);
		}
	}
}
