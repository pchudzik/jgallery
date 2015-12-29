package com.pchudzik.jgallery.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static com.pchudzik.jgallery.JGalleryConstatns.THUMBNAIL_EXECUTOR;

/**
 * Created by pawel on 01.12.15.
 */
@Configuration
@EnableAsync
public class AsyncConfiguration extends AsyncConfigurerSupport {
	@Value("${thumbnail.threads}")
	private int thumbnailThreads;

	@Bean(name = THUMBNAIL_EXECUTOR)
	@Override
	public Executor getAsyncExecutor() {
		final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

		taskExecutor.setThreadNamePrefix("thumbnail_");
		taskExecutor.setCorePoolSize(thumbnailThreads);
		taskExecutor.setBeanName(THUMBNAIL_EXECUTOR);
		taskExecutor.setMaxPoolSize(thumbnailThreads);

		return taskExecutor;
	}
}
