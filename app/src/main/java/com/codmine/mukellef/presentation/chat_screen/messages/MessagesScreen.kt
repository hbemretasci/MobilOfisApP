package com.codmine.mukellef.presentation.chat_screen.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.presentation.chat_screen.messages.components.DayHeader
import com.codmine.mukellef.presentation.chat_screen.messages.components.JumpToBottom
import com.codmine.mukellef.presentation.chat_screen.messages.components.MessageHeader
import com.codmine.mukellef.presentation.chat_screen.messages.components.MessageInput
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun MessagesScreen(
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    viewModel: MessagesViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val focusRequester = FocusRequester()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when(event) {
                is MessagesUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message.asString(context))
                }
                is MessagesUiEvent.SendMessageSuccess -> {
                    scrollState.scrollToItem(0)
                }
            }
        }
    }

   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(paddingValues)
   ) {
       MessageHeader(
           title = uiState.receiverName,
           modifier = Modifier.padding(MaterialTheme.spacing.medium),
       )
       Column(
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Box(
               modifier = Modifier.weight(.85f),
           ) {
               LazyColumn(
                   modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
                   reverseLayout = true,
                   state = scrollState,
               ) {
                   for (i in uiState.messages.indices) {
                       item {
                           // if ((!uiState.messages[i].status) && (uiState.messages[i].sender == uiState.receiverId))
                           MessageItem(uiState.messages[i], uiState.messages[i].sender == uiState.userId)
                           if (i + 1 < uiState.messages.size) {
                               if (uiState.messages[i].postDate != uiState.messages[i + 1].postDate) {
                                   DayHeader(uiState.messages[i].postDate)
                               }
                           } else {
                               DayHeader(uiState.messages[i].postDate)
                           }
                       }
                   }
               }
               // Jump to bottom button shows up when user scrolls past a threshold.
               // Convert to pixels:
               val jumpThreshold = with(LocalDensity.current) {
                   JumpToBottomThreshold.toPx()
               }
               // Show the button if the first visible item is not the first one or if the offset is
               // greater than the threshold.
               val jumpToBottomButtonEnabled by remember {
                   derivedStateOf {
                       scrollState.firstVisibleItemIndex != 0 ||
                               scrollState.firstVisibleItemScrollOffset > jumpThreshold
                   }
               }
               JumpToBottom(
                   // Only show if the scroller is not at the bottom
                   enabled = jumpToBottomButtonEnabled,
                   onClicked = {
                       scope.launch {
                           scrollState.animateScrollToItem(0)
                       }
                   },
                   modifier = Modifier.align(Alignment.BottomCenter)
               )
           }
           MessageInput(
               text = uiState.message,
               modifier = Modifier.weight(.15f),
               focusRequester = focusRequester,
               onValueChange = { viewModel.onEvent(MessagesEvent.MessageChanged(it)) },
               sendMessage = {
                   scope.launch {
                       viewModel.onEvent(MessagesEvent.PostMessage(it))
                   }
               }
           )
           if(uiState.errorStatus) {
               ReLoadData(
                   modifier = Modifier.fillMaxSize(),
                   errorMsg = uiState.errorText ?: UiText.StringResources(R.string.unexpected_error),
                   onRetry = { }
               )
           }
       }
   }

    DisposableEffect(viewModel) {
        viewModel.onEvent(MessagesEvent.AddMessagesListener)
        onDispose { viewModel.onEvent(MessagesEvent.RemoveMessagesListener) }
    }

}

@Composable
fun MessageItem(
    message: Message,
    isUserMe: Boolean
) {
    val meColor: Color = MaterialTheme.colorScheme.primary
    val opponentColor: Color = MaterialTheme.colorScheme.tertiary
    Box(
        contentAlignment = if (isUserMe) Alignment.CenterEnd else Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.small)
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 245.dp)
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.small
                )
                .drawBehind {
                    val cornerRadius = 10.dp.toPx()
                    val triangleHeight = 15.dp.toPx()
                    val triangleWidth = 20.dp.toPx()
                    val trianglePath = Path().apply {
                        if (isUserMe) {
                            moveTo(size.width, size.height - cornerRadius)
                            lineTo(size.width, size.height + triangleHeight)
                            lineTo(size.width - triangleWidth, size.height - cornerRadius)
                            close()
                        } else {
                            moveTo(0f, size.height - cornerRadius)
                            lineTo(0f, size.height + triangleHeight)
                            lineTo(triangleWidth, size.height - cornerRadius)
                            close()
                        }
                    }
                    drawPath(
                        path = trianglePath,
                        color = if (isUserMe) meColor else opponentColor
                    )
                }
                .background(
                    color = if (isUserMe) meColor else opponentColor,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(MaterialTheme.spacing.small)
        ) {
            Text(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                text = message.content,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isUserMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
            )
            Text(
                text = message.postTime,
                style = MaterialTheme.typography.labelSmall,
                color = if (isUserMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

private val JumpToBottomThreshold = 56.dp