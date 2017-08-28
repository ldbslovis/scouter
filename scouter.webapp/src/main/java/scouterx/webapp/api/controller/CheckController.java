package scouterx.webapp.api.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import scouterx.webapp.annotation.NoAuth;
import scouterx.webapp.api.exception.ErrorState;
import scouterx.webapp.api.exception.ErrorStateBizException;
import scouterx.webapp.api.exception.ErrorStateException;
import scouterx.webapp.api.fw.controller.ro.CommonResultView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by gunlee on 2017. 8. 24.
 */
@Path("/v0/check")
@Produces(MediaType.APPLICATION_JSON)
public class CheckController {

	@GET
	public String check(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.setAttribute("testId", "testIdValue");
		return "OK";
	}

	@GET @Path("/more")
	@NoAuth
	public String checkMore() {
		return "OK-More";
	}

	@GET @Path("/todo")
	public ToDo checkTodo() {
		return new ToDo("morning todo", "brew a coffee", new Job("mytypes", "jobname!!"));
	}

	@GET @Path("/todoAsResult")
	public CommonResultView<ToDo> checkTodoAsResult() {
		ToDo todo = new ToDo("morning todo", "brew a coffee", new Job("mytypes", "jobname!!"));
		CommonResultView<ToDo> resultView = CommonResultView.success(todo);
		return resultView;
	}

	@GET @Path("/exception")
	public CommonResultView<ToDo> exception() {
		if (true) {
			throw new RuntimeException("my exception");
		}
		return null;
	}

	@GET @Path("/exception/state")
	public CommonResultView<ToDo> exceptionState() {
		if (true) {
			throw new ErrorStateException(ErrorState.INTERNAL_SERVER_ERRROR, "test error state exception", new RuntimeException("my runtime ex!!!!!"));
		}
		return null;
	}

	@GET @Path("/exception/biz")
	public CommonResultView<ToDo> exceptionBizState() {
		if (true) {
			throw new ErrorStateBizException(ErrorState.INTERNAL_SERVER_ERRROR, "test error state BIZ !! exception");
		}
		return null;
	}

	@Setter
	@Getter
	@AllArgsConstructor
	public static class ToDo {
		String title;
		String desc;
		Job job2;
	}

	@Setter
	@Getter
	@AllArgsConstructor
	public static class Job {
		String type;
		String name;
	}
}
