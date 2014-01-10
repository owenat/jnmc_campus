asyncCall
		.reg(
				"runindex",
				function() {
					Page.$
							.domready(function() {
								Page.full = (function() {
									var k = document.createElement("div").style;
									var u = (function() {
										var B = "t,webkitT,MozT,msT,OT"
												.split(","), A, z = 0, y = B.length;
										for (; z < y; z++) {
											A = B[z] + "ransform";
											if (A in k) {
												return B[z].substr(0,
														B[z].length - 1)
											}
										}
										return false
									})();
									var l = function(y) {
										if (u === "") {
											return y
										}
										y = y.charAt(0).toUpperCase()
												+ y.substr(1);
										return u + y
									};
									var a = (function() {
										if (u === false) {
											return false
										}
										var y = {
											"" : "transitionend",
											webkit : "webkitTransitionEnd",
											Moz : "transitionend",
											O : "otransitionend",
											ms : "MSTransitionEnd"
										};
										return y[u]
									})();
									var g = l("perspective") in k;
									var i = l("transform");
									var v = l("transitionTimingFunction");
									var h = l("transitionDuration");
									var c = g ? " translateZ(0)" : "";
									var x = function(z, y) {
										this._init(z, y)
									};
									x.prototype = {
										_init : function(A, y) {
											if (!A) {
												return null
											}
											var E = this;
											var D, z = E.eventsName;
											var C = E.options = y || {};
											E.container = A;
											var B = E.element = y.panel
													|| A.children[0];
											E.index = C.startSlide || 0;
											E.dir = 1;
											E.speed = C.speed || 200;
											E.width = C.width;
											E.setWidth = !!C.width;
											E.useFreeze = /ipod/i
													.test(navigator.userAgent)
													|| /OS 4_/
															.test(navigator.userAgent);
											E.callback = C.callback
													|| function() {
													};
											E.delay = C.auto || 0;
											E.auto = !!Math.abs(C.auto);
											if (E.useFreeze && !E.auto) {
												d.addClass(E.container,
														"freeze");
												this.freezed = true
											}
											d.addClass(E.container, "unactive");
											E.enabled = false;
											E.setup();
											E.enable();
											C.oninit && C.oninit.call(E)
										},
										enable : function() {
											var z = this;
											var y = z.eventsName;
											if (z.enabled) {
												return
											}
											z.enabled = true;
											z.dir = 1;
											if (z.container.addEventListener) {
												n = y.length;
												while (n--) {
													z.container
															.addEventListener(
																	y[n], z,
																	false)
												}
											}
											nextFrame(function() {
												z.resume()
											})
										},
										disable : function() {
											var z = this;
											var y = z.eventsName;
											z.stop();
											z.slide(0);
											z.transitionEnd();
											z.enabled = false;
											z.dir = 1;
											if (z.container.addEventListener) {
												n = y.length;
												while (n--) {
													z.container
															.removeEventListener(
																	y[n], z,
																	false)
												}
											}
										},
										freeze : function(y) {
											var z = this;
											if (!z.useFreeze) {
												return
											}
											if ((y == this.gid) || this.auto
													|| this.freezed) {
												return
											}
											this.freezed = true;
											nextFrame(function() {
												d.addClass(z.container,
														"freeze")
											})
										},
										eventsName : [ "touchstart",
												"touchmove", "touchend",
												"touchcancel", a ],
										mround : function(y) {
											return y >> 0
										},
										setup : function() {
											var F = this;
											var z = F.container;
											var y;
											var E = z.getBoundingClientRect();
											F.slides = F.element.children;
											F.length = F.slides.length;
											var B = document.body.scrollTop;
											F.top = E.top + B;
											F.bottom = E.bottom + B;
											if (F.length < 2) {
												return
											}
											if (!F.setWidth) {
												y = Math
														.ceil(("getBoundingClientRect" in z) ? z
																.getBoundingClientRect().width
																: z.offsetWidth)
											} else {
												y = F.width
											}
											if (F.width == y) {
												return
											}
											F.width = y;
											if (!F.width) {
												return
											}
											if (!F.setWidth) {
												F.element.style.width = Math
														.ceil(F.slides.length
																* F.width)
														+ "px";
												var A = F.slides.length;
												while (A--) {
													var D = F.slides[A], C = D.style;
													C.width = F.width + "px"
												}
											}
										},
										slide : function(y, A) {
											var B = this;
											var z = B.element.style;
											if (A == undefined) {
												A = B.speed
											}
											nextFrame(function() {
												z[v] = "cubic-bezier(0.33,0.66,0.66,1)";
												z[v] = "ease-out";
												z[h] = A + "ms";
												z[i] = "translate("
														+ -(y * B.width)
														+ "px,0)" + c;
												d
														.removeClass(
																B.element.children[B.index],
																"current");
												B.index = y;
												d
														.addClass(
																B.element.children[B.index],
																"current")
											})
										},
										getPos : function() {
											return this.index
										},
										prev : function(y) {
											var z = this;
											nextFrame(function() {
												d.removeClass(z.container,
														"unactive")
											});
											clearTimeout(z.interval);
											if (z.index > 0) {
												z.index
														&& z.slide(z.index - 1,
																z.speed)
											} else {
												z.dir = 1;
												z.next()
											}
										},
										next : function(y) {
											var z = this;
											nextFrame(function() {
												d.removeClass(z.container,
														"unactive")
											});
											clearTimeout(z.interval);
											if (z.dir == 1) {
												if (z.index < z.length - 1) {
													z.slide(z.index + 1,
															this.speed)
												} else {
													z.dir = -1;
													z.prev()
												}
											} else {
												z.prev()
											}
										},
										begin : function() {
											var y = this;
											y.interval = (y.delay) ? setTimeout(
													function() {
														y.dir == 1 ? y
																.next(y.delay)
																: y
																		.prev(y.delay)
													}, y.delay)
													: 0
										},
										stop : function() {
											var y = this;
											y.delay = 0;
											clearTimeout(y.interval)
										},
										resume : function() {
											var y = this;
											y.delay = y.options.auto || 0;
											y.begin()
										},
										eventMap : (function() {
											var y = {
												touchstart : "onTouchStart",
												touchmove : "onTouchMove",
												touchcancel : "onTouchEnd",
												touchend : "onTouchEnd"
											};
											y[a] = "transitionEnd";
											return y
										})(),
										handleEvent : function(z) {
											var y = this["eventMap"][z.type];
											y && this[y](z)
										},
										transitionEnd : function(y) {
											var z = this;
											nextFrame(function() {
												z.callback(y, z.index,
														z.slides[z.index])
											});
											z.delay && z.begin()
										},
										active : function() {
											this.transitionEnd({})
										},
										onTouchStart : function(z) {
											var A = this;
											var y = A.element.style;
											A.start = {
												pageX : A
														.mround(z.touches[0].pageX),
												pageY : A
														.mround(z.touches[0].pageY),
												time : Number(new Date())
											};
											clearTimeout(A.interval);
											A.isScrolling = undefined;
											A.deltaX = 0;
											y[h] = 0;
											y = null;
											A.moveCount = 0
										},
										onTouchMove : function(C) {
											var D = this;
											var B = D.element.style;
											var z;
											var y = 0;
											var A = (D.length - 1) * D.width
													* (-1);
											if (C.touches.length > 1 || C.scale
													&& C.scale !== 1) {
												B = null;
												return
											}
											D.deltaX = D
													.mround(C.touches[0].pageX
															- D.start.pageX);
											if (typeof D.isScrolling == "undefined") {
												D.isScrolling = !!(D.isScrolling || Math
														.abs(D.deltaX) < Math
														.abs(C.touches[0].pageY
																- D.start.pageY))
											}
											if (!D.isScrolling) {
												if (D.useFreeze && !D.auto) {
													if (D.freezed) {
														d.removeClass(
																D.container,
																"freeze");
														D.freezed = false;
														D.useFreeze
																&& Page.swipeMgr
																		.freeze(D.gid)
													}
												}
												if (!D.moveCount) {
													d.addClass(D.container,
															"has3d");
													d.removeClass(D.container,
															"unactive")
												}
												z = D.deltaX - D.index
														* D.width;
												z = Math.max(Math.min(z, y), A);
												if (z == A || z == y) {
													return
												}
												C.preventDefault();
												B[i] = "translate(" + z
														+ "px,0)" + c;
												D.moveCount++
											}
											B = null
										},
										onTouchEnd : function(A) {
											var B = this;
											var z = Number(new Date())
													- B.start.time < 350
													&& Math.abs(B.deltaX) > 15
													|| Math.abs(B.deltaX) > B.width / 3, y = !B.index
													&& B.deltaX > 0
													|| B.index == B.length - 1
													&& B.deltaX < 0;
											(B.deltaX >= 0 && z && !y) ? B.dir = 1
													: B.dir = -1;
											if (z && !this.isScrolling) {
												A.preventDefault()
											}
											if (!this.isScrolling) {
												this
														.slide(
																this.index
																		+ (z
																				&& !y ? (this.deltaX < 0 ? 1
																				: -1)
																				: 0),
																this.speed)
											}
											this.isScrolling = true
										}
									};
									var d = Page.$;
									Page.swipeMgr = (function() {
										var H = [];
										var I = (function() {
											var N = 0;
											return function() {
												return N++
											}
										})();
										var y;
										var C = /ipod/i
												.test(navigator.userAgent);
										if (/android.*mqqbrowser\/4\.[01234]/i
												.test(navigator.userAgent)) {
											var L;
											setInterval(function() {
												if (L != window.innerWidth) {
													L = window.innerWidth;
													if (L) {
														B()
													}
												}
											}, 80)
										}
										var D = function(N, O) {
											O && O.enable()
										};
										var J = function(N, O) {
											O && O.disable()
										};
										var K = function(N, O) {
											if (!C) {
												return
											}
											O && O.freeze(y)
										};
										var G = function() {
											nextFrame(function() {
												d.each(H, D)
											})
										};
										var E = function() {
											nextFrame(function() {
												d.each(H, J)
											})
										};
										var z = function(N) {
											if (y == N) {
												return
											}
											y = N;
											nextFrame(function() {
												d.each(H, K)
											})
										};
										var F = function(O, N) {
											var P = new x(O, N);
											P.gid = N.id || I();
											H.push(P);
											return P
										};
										var M = function(N, O) {
											O && O.setup()
										};
										var B = function() {
											nextFrame(function() {
												d.each(H, M)
											})
										};
										var A = function(O) {
											var N;
											d.each(H, function(P, Q) {
												if (Q.gid == O) {
													N = Q
												}
											});
											return N
										};
										window.addEventListener("resize", B,
												false);
										return {
											enable : G,
											disable : E,
											freeze : z,
											create : F,
											get : A,
											arr : H
										}
									})();
									var m = function() {
										var y = d.id("slideImage");
										var B = d.cls("dot", d.id("slideDots"));
										var z = d.tag("a", y);
										var A = d.tag("img", y);
										d.loadImg(A);
										Page.swipeMgr
												.create(
														y,
														{
															auto : 3000,
															id : "imgslide",
															callback : function(
																	E, D) {
																var F = (d.tag(
																		"img",
																		z[D])[0] || {}).alt
																		|| "";
																var C = z[D].href;
																d
																		.id("slideImageTitile").innerHTML = '<a href="'
																		+ C
																		+ '">'
																		+ F
																		+ "</a>";
																d
																		.each(
																				B,
																				function(
																						G,
																						H) {
																					G == D ? d
																							.addClass(
																									H,
																									"current")
																							: d
																									.removeClass(
																											H,
																											"current")
																				})
															},
															oninit : function() {
																var C = d
																		.cls(
																				"dot-slider",
																				this.element.parentNode.parentNode.parentNode.parentNode)[0];
																C
																		&& d
																				.addClass(
																						C,
																						"enable")
															}
														})
									};
									var p = [ "news", "finance", "sports",
											"ent", "tech", "auto", "lady",
											"edu" ];
									var o = function(C, B) {
										var F = d.id("mod-" + B);
										var z = d.cls("module-c", F)[0];
										var A = d.tag("a", d.cls("more", F)[0]);
										var D = d.tag("a",
												d.cls("module-t", F)[0])[0];
										var E = d.cls("dot", F);
										var y = [];
										d.each(A, function(G, H) {
											y.push([ H.innerHTML, H.href ])
										});
										Page.swipeMgr
												.create(
														F,
														{
															auto : 0,
															panel : z,
															callback : function(
																	H, G) {
																D.innerHTML = y[G][0];
																D.href = y[G][1];
																d
																		.removeClass(
																				E,
																				"current");
																d
																		.addClass(
																				E[G],
																				"current");
																if (B == "news"
																		&& d
																				.id("hd-tips").style.display != "none") {
																	d
																			.id("hd-tips").style.display = "none"
																}
															},
															oninit : function() {
																var G = d
																		.cls(
																				"dot-slider",
																				this.element.parentNode)[0];
																G
																		&& d
																				.addClass(
																						G,
																						"enable")
															}
														})
									};
									var j = function() {
										d.each(p, o)
									};
									var f = function() {
										var A = d.id("product-list");
										var y = d.cls("boutique-list", A)[0];
										var z = d.tag("span", d.cls(
												"boutique-slider", A)[0]);
										Page.swipeMgr
												.create(
														A,
														{
															auto : 0,
															width : 188,
															panel : y,
															id : "prolist",
															callback : function(
																	C, B) {
																var D;
																d
																		.removeClass(
																				z,
																				"current");
																d
																		.addClass(
																				z[B],
																				"current");
																if (Page.conf.current == "full") {
																	if (d
																			.hasClass(
																					d
																							.id("openprolist"),
																					"open")) {
																		return
																	}
																	D = d
																			.tag(
																					"img",
																					d
																							.id("prolist-holder"));
																	if (D.length == 0) {
																		return
																	}
																	d
																			.each(
																					[
																							B - 2,
																							B - 1,
																							B,
																							B + 1,
																							B + 2 ],
																					function(
																							F,
																							H) {
																						var E = D[Math
																								.min(
																										Math
																												.max(
																														0,
																														H),
																										D.length - 1)];
																						var G = E
																								.getAttribute("lazysrc");
																						if (G) {
																							E.src = G;
																							E
																									.removeAttribute("lazysrc")
																						}
																					})
																}
															},
															oninit : function() {
																var B = d
																		.cls(
																				"dot-slider",
																				this.element.parentNode)[0];
																B
																		&& d
																				.addClass(
																						B,
																						"enable")
															}
														})
									};
									var t = function(z) {
										var B = t.div
												|| document
														.createElement("div");
										var y = document
												.createDocumentFragment();
										t.div = B;
										B.innerHTML = z;
										var A;
										while (A = B.children[0]) {
											y.appendChild(A)
										}
										return y
									};
									var b = function(F, E, y) {
										var C = d.id("mod-" + F);
										if (!C) {
											return
										}
										var z = d.cls("dot-slider", C)[0];
										var D = d.cls("module-c", C)[0];
										var B = d.cls("panel", C)[0];
										var A = "";
										d
												.each(
														E,
														function(G, H) {
															A += '<div class="panel fullver" style="width:'
																	+ y
																	+ 'px"><ul class="cont-list">';
															d
																	.each(
																			H.data,
																			function(
																					I,
																					J) {
																				A += '<li><a class="'
																						+ (J.isHasVideo ? "v-link"
																								: "")
																						+ '" href="'
																						+ J.link
																						+ "&pos="
																						+ J.pos
																						+ "&iarea="
																						+ H.iarea
																						+ '&icfa=home_touch">'
																						+ J.title
																						+ '<span class="cmt-num">'
																						+ J.cmtcnt
																						+ "</span></a></li>"
																			});
															A += "</ul></div>"
														});
										B.style.width = y + "px";
										D.style.width = (y * (E.length + 1))
												+ "px";
										D.appendChild(t(A));
										C = null;
										z = null
									};
									var w = false;
									var r = false;
									var q = function() {
										r = true;
										m();
										f()
									};
									var e = function() {
										Page.swipeMgr.disable()
									};
									var s = function() {
										nextFrame(function() {
											!r && q();
											if (!w) {
												var z = d.id("mod-news");
												var y = Math
														.ceil(("getBoundingClientRect" in z) ? z
																.getBoundingClientRect().width
																: z.offsetWidth);
												asyncCall
														.reg(
																"renderTab",
																function(B) {
																	if (w) {
																		return
																	}
																	w = true;
																	var C = B.data;
																	var A;
																	if (!C) {
																		return
																	}
																	for (A in C) {
																		if (C
																				.hasOwnProperty(A)) {
																			nextFrame((function(
																					D) {
																				return function() {
																					b(
																							D,
																							C[D],
																							y);
																					o(
																							null,
																							D)
																				}
																			})
																					(A))
																		}
																	}
																})
											} else {
												Page.swipeMgr.enable()
											}
										})
									};
									return {
										enter : s,
										leave : e
									}
								})();
								(Page.conf.current == "full")
										&& Page.full.enter()
							})
				});