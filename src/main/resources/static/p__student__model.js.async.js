(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[22],{AxU6:function(e,t,a){"use strict";a.r(t);var s=a("MVZn"),n=a.n(s),r=a("o0o1"),u=a.n(r),c=a("abFe"),d=a("GGqY");t["default"]={namespace:"studentModel",state:{page:0,size:10,totalElements:0,totalPages:0,studentsByEdu:[]},subscriptions:{},effects:{deleteStudent(e,t){return u.a.mark(function a(){var s,n,r,o,p,l;return u.a.wrap(function(a){while(1)switch(a.prev=a.next){case 0:return s=e.payload,n=t.call,r=t.put,o=t.select,a.next=4,o(e=>e.studentModel.studentsByEdu);case 4:return p=a.sent,a.next=7,n(c["g"],s);case 7:if(l=a.sent,!l.success){a.next=15;break}return p.splice(p.findIndex(e=>e.studentId===l.object),1),a.next=12,r({type:"updateState",payload:{studentsByEdu:p}});case 12:d["b"].success(l.message),a.next=16;break;case 15:d["b"].error(l.message);case 16:case"end":return a.stop()}},a)})()},addStudent(e,t){return u.a.mark(function a(){var s,n,r,o;return u.a.wrap(function(a){while(1)switch(a.prev=a.next){case 0:return s=e.payload,n=t.call,r=t.put,a.next=4,n(c["b"],s);case 4:if(o=a.sent,!o.success){a.next=11;break}return a.next=8,r({type:"updateState",payload:{showModal:!1}});case 8:d["b"].success(o.message),a.next=12;break;case 11:d["b"].error(o.message);case 12:case"end":return a.stop()}},a)})()},addPayment(e,t){return u.a.mark(function a(){var s,n,r,o;return u.a.wrap(function(a){while(1)switch(a.prev=a.next){case 0:return s=e.payload,n=t.call,r=t.put,a.next=4,n(c["a"],s);case 4:if(o=a.sent,!o.success){a.next=11;break}return a.next=8,r({type:"updateState",payload:{showModal:!1}});case 8:d["b"].success(o.message),a.next=12;break;case 11:d["b"].error(o.message);case 12:case"end":return a.stop()}},a)})()},getStudentByEdu(e,t){return u.a.mark(function a(){var s,n,r,d,o,p,l,i;return u.a.wrap(function(a){while(1)switch(a.prev=a.next){case 0:if(s=e.payload,n=t.call,r=t.put,d=t.select,s){a.next=9;break}return a.next=5,d(e=>e.studentModel);case 5:o=a.sent,p=o.page,l=o.size,s={page:p,size:l};case 9:return a.next=11,n(c["u"],s);case 11:if(i=a.sent,!i.success){a.next=15;break}return a.next=15,r({type:"updateState",payload:{studentsByEdu:i.object,page:i.page,size:i.size,totalElements:i.totalElements,totalPages:i.totalPages}});case 15:case"end":return a.stop()}},a)})()}},reducers:{updateState(e,t){var a=t.payload;return n()({},e,a)}}}}}]);